package uz.softex.carsale.config

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import uz.softex.carsale.user.Users
import java.sql.Timestamp

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class AbstractEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Int? = null,

    @Column(updatable = false)
    @CreationTimestamp
    val createdTime: Timestamp? = null,

    @UpdateTimestamp
    val updatedAt: Timestamp? = null,

//    @JoinColumn(updatable = false)
//    @CreatedBy
//    @ManyToOne(fetch = FetchType.LAZY)
//    val createdByUser: Users? = null,

//    @LastModifiedBy
//    @ManyToOne(fetch = FetchType.LAZY)
//    val updatedByUser: Users? = null,

    )
