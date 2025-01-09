package org.polarmeet.postgresqljsonb.repository

import org.polarmeet.postgresqljsonb.entities.MedicalInstitution
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Repository
interface MedicalInstitutionRepository : ReactiveCrudRepository<MedicalInstitution, UUID> {
    // Custom query to search by contact information fields within JSONB
    @Query("""
        SELECT * FROM medical_institutions 
        WHERE contact_information->>'email' = :email
    """)
    fun findByContactEmail(email: String): Flux<MedicalInstitution>

    @Query("""
        SELECT * FROM medical_institutions 
        WHERE license_number = :licenseNumber
    """)
    fun findByLicenseNumber(licenseNumber: String): Mono<MedicalInstitution>
}
