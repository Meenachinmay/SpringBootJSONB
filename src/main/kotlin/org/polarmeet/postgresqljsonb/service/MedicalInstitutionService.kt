package org.polarmeet.postgresqljsonb.service

import org.polarmeet.postgresqljsonb.entities.MedicalInstitution
import org.polarmeet.postgresqljsonb.models.ContactInformation
import org.polarmeet.postgresqljsonb.repository.MedicalInstitutionRepository
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDateTime
import java.util.*

@Service
class MedicalInstitutionService(
    private val repository: MedicalInstitutionRepository,
    private val errorAttributes: DefaultErrorAttributes
) {
    fun updateInstitutionContact(
        institutionId: UUID,
        newContact: ContactInformation
    ): Mono<MedicalInstitution> {
        return repository.findById(institutionId)
            .map { institution ->
                institution.copy(
                    contactInformation = newContact,
                    updatedAt = LocalDateTime.now()
                )
            }
            .flatMap { repository.save(it) }
    }

    fun createInstitution(institution: MedicalInstitution): Mono<MedicalInstitution> {
        return repository.findByLicenseNumber(institution.licenseNumber)
            .flatMap<MedicalInstitution> {
                // if we found an existing record throw an error
                Mono.error(IllegalStateException("Institution already exists with license number: ${institution.licenseNumber}"))
            }
            .switchIfEmpty(
                // if no existing institution found, proceed with save
                repository.save(institution)
            )
    }

    fun findInstitutionByEmail(email: String): Flux<MedicalInstitution> {
        return repository.findByContactEmail(email)
    }

    fun findInstitutionByLicenseNumber(licenseNumber: String): Mono<MedicalInstitution> {
       return repository.findByLicenseNumber(licenseNumber)
    }
}