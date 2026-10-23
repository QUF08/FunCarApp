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
    var topSpeed: Int,
    var oil: FuelType
)
fun Car.addDownhillSpeed(): Car {
    return this.copy(topSpeed = this.topSpeed + 20)
}
fun Car.resetStraightSpeed(): Car {
    return this.copy(topSpeed = this.topSpeed - 20)
}