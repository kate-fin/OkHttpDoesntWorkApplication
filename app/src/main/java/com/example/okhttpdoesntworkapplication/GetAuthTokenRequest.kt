package com.example.okhttpdoesntworkapplication

class GetAuthTokenRequest {
    var login: String = ""
    var password: String = ""
    var returnUrl: String = "/"

    constructor(
        login: String,
        password: String,
    ) {
        this.login = login
        this.password = password
    }
}