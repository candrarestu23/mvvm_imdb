package com.candra.myapplication.base

class ResponseObject<Model> : BaseResponse() {
    var results: Model ?= null
    val page: Int? = null
    val total_page: Int? = null}