package com.chin.ClassroomApi.Controllers

import com.chin.ClassroomApi.Entities.UserEntity
import com.chin.ClassroomApi.Repositories.UserRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/")
class UserController {
    @Autowired
    lateinit var repository: UserRepository
    @GetMapping("all")
    @Transactional
    fun getAll(): List<UserEntity> = repository.findAll()

    @PostMapping("user")
    @Transactional
    fun createUser(@RequestBody user: UserEntity): String {
        repository.save(user)
        return "ok"
    }

}