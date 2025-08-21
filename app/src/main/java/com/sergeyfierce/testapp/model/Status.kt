enum class Status(val title: String, val color: Long) {
    ACTIVE("Активный", 0xFF4CAF50),
    PASSIVE("Пассивный", 0xFFFF9800),
    LOST("Потерянный", 0xFFF44336),
    COLD("Холодный", 0xFF2196F3),
    WARM("Тёплый", 0xFFE91E63)
}
