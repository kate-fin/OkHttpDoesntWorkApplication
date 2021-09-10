package com.example.okhttpdoesntworkapplication

import ru.spaceapp.infocity.android.sal.enums.ResponseStatus

class ResponseWrapper<T> {
    var status: ResponseStatus
    var responseData: T? = null

    constructor(status: ResponseStatus, responseData: T?) {
        this.status = status
        this.responseData = responseData
    }
}