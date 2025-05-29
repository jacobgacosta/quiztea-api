package io.quiztea.extensions

data class Page(
    val content: List<Map<String, Any>>,
    val pageNumber: Int,
    val pageSize: Int,
    val totalElements: Int,
    val totalPages: Int,
    val hasNext: Boolean,
    val hasPrevious: Boolean
)
