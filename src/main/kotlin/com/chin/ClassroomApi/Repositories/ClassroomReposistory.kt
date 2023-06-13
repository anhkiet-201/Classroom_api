package com.chin.ClassroomApi.Repositories

import com.chin.ClassroomApi.Entities.ClassroomEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ClassroomRepository: JpaRepository<ClassroomEntity, String> {
}