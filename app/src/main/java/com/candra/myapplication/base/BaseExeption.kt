package com.candra.myapplication.base

class BaseExeption : Exception {

    val responseCode: Int
    val response: BaseResponse

    constructor(mBaseResponse: BaseResponse) {
        this.response = mBaseResponse
        this.responseCode = 200
    }

    constructor(baseResponse: BaseResponse, responseCode: Int) {
        this.response = baseResponse
        this.responseCode = responseCode
    }
}
