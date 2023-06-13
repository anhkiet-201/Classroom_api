package com.chin.ClassroomApi.Controllers

import com.chin.ClassroomApi.DTO.Request.UpdateUserRequest
import com.chin.ClassroomApi.Entities.UserEntity
import com.chin.ClassroomApi.Services.UserServices.UserService
import com.chin.ClassroomApi.Utils.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user")
class UserController(
    val userService: UserService,
) {
    @GetMapping
    fun getUser(id: String): JsonResponseType {
        val user = userService.findById(id)
        return user.response(
            success = {
                ResponseBuilder(
                    ResponseBuilder.OK,
                    "ok", it
                )
            },
            error = {
                ResponseBuilder(
                    ResponseBuilder.NOT_FOUND,
                    "Error",
                    "User not found!"
                    )
            }
        )
    }
    @PutMapping
    fun updateUser(payload: UpdateUserRequest): JsonResponseType {
        return userService.updateUser(payload).responseCustom {
            when(it) {
                null -> ResponseBuilder(
                    ResponseBuilder.NOT_FOUND,
                    "Failure",
                    "User not found",
                )
                else -> ResponseBuilder(
                    ResponseBuilder.OK,
                    "Success",
                    it
                )
            }
        }
    }
    @GetMapping("class")
    fun getClass(id: String): JsonResponseType {
        val user = userService.getClassroomWasCreatedById(id)
        return user.responseCustom {
            when(it) {
                null -> ResponseBuilder(
                    ResponseBuilder.NOT_FOUND,
                    "Failure",
                    "User not found",
                )
                else -> ResponseBuilder(
                    ResponseBuilder.OK,
                    "Success",
                    it
                )
            }
        }
    }

}
