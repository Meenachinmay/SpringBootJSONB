package org.polarmeet.postgresqljsonb.entities

import org.polarmeet.postgresqljsonb.models.ContactInformation
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.util.*

// Entity class using this contact information
@Table("medical_institutions")
data class MedicalInstitution(
    @Id
    @Column("institution_id")
    val institutionId: UUID? = null,

    @Column("institution_name")
    val institutionName: String,

    @Column("institution_type")
    val institutionType: String,

    @Column("address")
    val address: String,

    @Column("license_number")
    val licenseNumber: String,

    @Column("contact_information")
    val contactInformation: ContactInformation,

    @Column("created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column("updated_at")
    val updatedAt: LocalDateTime = LocalDateTime.now()
)