package com.example.okhttpdoesntworkapplication

data class PersonModel(
	val id: String? = "",
	val isActive: Boolean = false,
	val fullName: String? = "",
	val firstName: String? = null,
	val middleName: String? = null,
	val lastName: String? = null,
	val email: String? = null,
	val isEmailConfirmed: Boolean = false,
	val phone: String? = null,
	val isPhoneConfirmed: Boolean = false,
	val activeCompanyId: String? = null
)
