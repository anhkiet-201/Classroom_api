package com.chin.ClassroomApi.Entities

import jakarta.persistence.*

@Entity
@Table(name = "classrooms")
data class ClassroomEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "class_id")
    var id: String = "",

    @Column(name = "class_name")
    var classname: String,

    @Column(name = "tern")
    var tern: String = "",

    @Column(name = "short_description")
    var shortDescription: String,

    @Column(name = "class_photo_url")
    var photoUrl: String = "",

    /// Relationship
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "class_owner_id", referencedColumnName = "user_id")
    var classOwner: UserEntity,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "class_members", joinColumns = [JoinColumn(name = "class_id", referencedColumnName = "class_id")], inverseJoinColumns = [JoinColumn(name = "user_id", referencedColumnName = "user_id")])
    var classMembers: List<UserEntity>

)
