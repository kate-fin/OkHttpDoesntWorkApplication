package com.example.okhttpdoesntworkapplication

import com.google.gson.annotations.Expose

class GetUserRequest(
    @Expose(serialize = false)
    override var token: String,
//    @Expose(serialize = false)
//    var id: String,
) : AuthorizedRequest {
}