data class Contact(
    val id: Int,
    val fullName: String,
    val status: Status,
    val tags: List<String> = emptyList(),
    val category: Category
)
