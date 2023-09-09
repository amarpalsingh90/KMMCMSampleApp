package model

import kotlinx.serialization.Serializable


@Serializable
data class ProductDetail(
    val description: String,
    val id: Int,
    val image: String,
    val name: String,
    val price: Int
)