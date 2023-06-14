package com.chin.ClassroomApi.Controllers

import com.chin.ClassroomApi.DTO.Request.LoginRequest
import com.chin.ClassroomApi.DTO.Request.RegisterRequest
import com.chin.ClassroomApi.DTO.Response.AuthResponse
import com.chin.ClassroomApi.Entities.UserEntity
import com.chin.ClassroomApi.Services.JwtServices.TokenService
import com.chin.ClassroomApi.Services.UserServices.UserService
import com.chin.ClassroomApi.Utils.*
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class AuthController(
        private val tokenService: TokenService,
        private val userService: UserService
) {
    @PostMapping("/login")
    fun login(@RequestBody payload: LoginRequest): JsonResponseType {
        val user = userService.findByEmail(payload.email)
        return user.responseCustom {
            when (it) {
                null -> ResponseBuilder(
                        ResponseBuilder.UNAUTHORIZED,
                        "Login failure",
                        "reason" to "Email not match!"
                )

                else -> if (!BCrypt.checkpw(payload.password, it.password)) ResponseBuilder(
                        ResponseBuilder.UNAUTHORIZED,
                        "Login failure",
                        "reason" to "Password not match!"
                ) else ResponseBuilder(
                        ResponseBuilder.OK,
                        "Login successful!",
                        "token" to tokenService.createToken(payload.email),
                        "user" to it
                )
            }
        }
    }

    @PostMapping("/register")
    fun register(@RequestBody payload: RegisterRequest): JsonResponseType {
        if (userService.existsByEmail(payload.email)) return responseCustom {
            ResponseBuilder(
                    ResponseBuilder.UNAUTHORIZED,
                    "Register failure",
                    "reason" to "Email already exists",
            )
        }
        val user = UserEntity(
                email = payload.email,
                displayName = payload.userName,
                photoUrl = "",
                password = BCrypt.hashpw(payload.password, BCrypt.gensalt()),
                birthday = payload.birthDay,
                classroomEntity = null,
                joinClasses = null
        )
        val savedUser = this.userService.save(user)
        return responseCustom {
            ResponseBuilder(
                    ResponseBuilder.OK,
                    "Register successful!",
                    "token" to tokenService.createToken(payload.email),
                    "user" to savedUser
            )
        }
    }

}