package org.polarmeet.postgresqljsonb.controller

import org.polarmeet.postgresqljsonb.entities.MedicalInstitution
import org.polarmeet.postgresqljsonb.models.ContactInformation
import org.polarmeet.postgresqljsonb.models.InstitutionRequest
import org.polarmeet.postgresqljsonb.service.MedicalInstitutionService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.util.*

@RestController
@RequestMapping("/institutions")
class MedicalInstitutionController(
    private val service: MedicalInstitutionService
) {

    @PostMapping
    fun createInstitution(@RequestBody request: InstitutionRequest): Mono<ResponseEntity<Any>> {
        val institution = MedicalInstitution(
            institutionId = null,
            institutionName = request.institutionName,
            institutionType = request.institutionType,
            address = request.address,
            licenseNumber = request.licenseNumber,
            contactInformation = request.contactInformation
        )

        return service.createInstitution(institution)
            .map { institution -> ResponseEntity.ok().body(institution as Any) }
            .onErrorResume { e ->
                when (e) {
                    is IllegalStateException -> Mono.just(
                        ResponseEntity
                            .status(HttpStatus.CONFLICT)
                            .body(mapOf("error" to e.message) as Any)
                    )
                    else -> Mono.just(
                        ResponseEntity
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(mapOf("error" to "An unexpected error occurred") as Any)
                    )
                }
            }
    }

    @GetMapping("/health")
    fun healthCheck() = "Medical Service is UP"

    @PutMapping("/{institutionId}/contact")
    fun updateContact(
        @PathVariable institutionId: UUID,
        @RequestBody contactInfo: ContactInformation
    ): Mono<MedicalInstitution> {
        return service.updateInstitutionContact(institutionId, contactInfo)
    }

    // fetch a license number
    @GetMapping("/license/{licenseNumber}")
    fun getInstitutionByLicenseNumber(@PathVariable licenseNumber: String): Mono<MedicalInstitution> {
        return service.findInstitutionByLicenseNumber(licenseNumber)
    }

}