package com.chin.ClassroomApi.Controllers

import com.chin.ClassroomApi.DTO.Request.LoginRequest
import com.chin.ClassroomApi.DTO.Request.RegisterRequest
import com.chin.ClassroomApi.DTO.Response.LoginResponse
import com.chin.ClassroomApi.Entities.UserEntity
import com.chin.ClassroomApi.Services.JwtServices.TokenService
import com.chin.ClassroomApi.Services.UserServices.UserService
import com.chin.ClassroomApi.Utils.ResponseObject
import jakarta.validation.constraints.Email
import org.apache.juli.logging.Log
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.logging.Logger

@RestController
@RequestMapping("/api")
class AuthController(
    private val tokenService: TokenService,
    private val userService: UserService
) {
    @PostMapping("/login")
    fun login(@RequestBody payload: LoginRequest): ResponseEntity<ResponseObject> {

        val user = userService.findByEmail(payload.email) ?: return ResponseEntity.status(400).body(
            ResponseObject(
                401,
                "Login failure",
                "email not match!"
            )
        )

        if (!BCrypt.checkpw(payload.password, user.password)) {
            return ResponseEntity.status(400).body(
                ResponseObject(
                    401,
                    "Login failure",
                    "password not match!"
                )
            )
        }

        return ResponseEntity.ok(
            ResponseObject(
                200,
                "Login successful!",
                LoginResponse(
                    tokenService.createToken(payload.email),
                    user
                )
            )
        )
    }

    @PostMapping("/register")
    fun register(@RequestBody payload: RegisterRequest ): ResponseEntity<ResponseObject> {
        if (userService.existsByEmail(payload.email)) {
            return ResponseEntity.status(401).body(
                ResponseObject(
                    401,
                    "Register failure",
                    "Email already exists"
                )
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

        val savedUser = userService.save(user)
        return ResponseEntity.ok(
            ResponseObject(
                200,
                "Register successful!",
                LoginResponse(
                    tokenService.createToken(payload.email),
                    savedUser
                )
            )
        )
    }

}