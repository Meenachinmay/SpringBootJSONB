package org.polarmeet.postgresqljsonb.controller

import org.polarmeet.postgresqljsonb.entities.MedicalInstitution
import org.polarmeet.postgresqljsonb.models.ContactInformation
import org.polarmeet.postgresqljsonb.models.InstitutionRequest
import org.polarmeet.postgresqljsonb.service.MedicalInstitutionService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.util.*

@RestController
@RequestMapping("/api/institutions")
class MedicalInstitutionController(
    private val service: MedicalInstitutionService
) {

    @PostMapping
    fun createInstitution(@RequestBody request: InstitutionRequest): Mono<MedicalInstitution> {
        val institution = MedicalInstitution(
            institutionId = null,  // Let it be generated during save
            institutionName = request.institutionName,
            institutionType = request.institutionType,
            address = request.address,
            licenseNumber = request.licenseNumber,
            contactInformation = request.contactInformation
        )

        return service.createInstitution(institution)
    }

    @PutMapping("/{institutionId}/contact")
    fun updateContact(
        @PathVariable institutionId: UUID,
        @RequestBody contactInfo: ContactInformation
    ): Mono<MedicalInstitution> {
        return service.updateInstitutionContact(institutionId, contactInfo)
    }
}