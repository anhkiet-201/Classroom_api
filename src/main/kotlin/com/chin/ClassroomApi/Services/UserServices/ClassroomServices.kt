package com.chin.ClassroomApi.Services.UserServices

import com.chin.ClassroomApi.Entities.ClassroomEntity
import com.chin.ClassroomApi.Repositories.ClassroomRepository
import org.springframework.stereotype.Service

@Service
class ClassroomServices(
    val classRespository: ClassroomRepository
) {

}