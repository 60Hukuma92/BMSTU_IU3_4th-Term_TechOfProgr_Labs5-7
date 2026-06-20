package com.test.magicalhaven.server.repository.entity

import jakarta.persistence.*

@Entity
@Table(name = "players")
class PlayerEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(nullable = false, unique = true)
    var username: String = "",

    @Column(nullable = false)
    var balance: java.math.BigDecimal = java.math.BigDecimal.ZERO,

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    var adoptedCreatures: MutableList<CreatureEntity> = mutableListOf()
)
