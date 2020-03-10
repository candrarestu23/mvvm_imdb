package com.candra.myapplication.base

class ResponseArray<Model> : BaseResponse() {
    val results: List<Model>? = null
    val page: Int? = null
    val total_page: Int? = null
}
