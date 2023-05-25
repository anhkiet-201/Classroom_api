package com.chin.ClassroomApi.Entities

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
@Table(name = "users")
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    var id: String = "",

    @Column(name = "email", unique = true)
    var email: String,

    @Column(name = "display_name")
    var displayName: String,

    @Column(name = "photo_url")
    var photoUrl: String,

    @Column(name = "birthday")
    var birthday: Long,

    @JsonIgnore
    @Column(name = "password")
    var password: String,

    /// Relationship

    @OneToOne(mappedBy = "classOwner")
    var classroomEntity: ClassroomEntity?,

    @ManyToMany(mappedBy = "classMembers", fetch = FetchType.EAGER)
    var joinClasses: List<ClassroomEntity>?
)
