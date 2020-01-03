package com.priyank.mvvmdemo.presentation.home.model

data class UserListMainModel(
    val `data`: List<Data> = listOf(),
    val page: Int = 0,
    val per_page: Int = 0,
    val total: Int = 0,
    val total_pages: Int = 0
) {
    data class Data(
        val avatar: String = "",
        val email: String = "",
        val first_name: String = "",
        val id: Int = 0,
        val last_name: String = ""
    )
}