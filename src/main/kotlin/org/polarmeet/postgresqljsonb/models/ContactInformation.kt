package org.polarmeet.postgresqljsonb.models

data class ContactInformation(
    val email: String,
    val phone: String,
    val website: String?,
    val emergencyContact: String?
)

data class InstitutionRequest(
    val institutionName: String,
    val institutionType: String,
    val address: String,
    val licenseNumber: String,
    val contactInformation: ContactInformation
)