package com.pgkchamps.lessoncompose.data

enum class FuelType(val displayName: String) {
    AI_92("АИ-92"),
    AI_95("АИ-95"),
    AI_100("АИ-100"),
    DT("Дизель")
}
data class Car(
    val brand: String,
    val mark: String,
    var milliage: Int,
    val topSpeed: Int,
    var oil: FuelType
)