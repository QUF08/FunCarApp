package com.pgkchamps.lessoncompose.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pgkchamps.lessoncompose.data.Car
import com.pgkchamps.lessoncompose.data.FuelType

@Composable
fun HomeScreen(onCarCreated: (Car) -> Unit) {
    // Переменные для хранения того, что пишет пользователь в реальном времени
    var brand by remember { mutableStateOf("") }
    var mark by remember { mutableStateOf("") }
    var milliage by remember { mutableStateOf("") }
    var topSpeed by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(32.dp))

        Text(
            text = "Добавь свой автомобиль",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Green,
            modifier = Modifier.padding(bottom = 24.dp),
        )

        // 1. Поле для ввода бренда
        OutlinedTextField(
            value = brand,
            onValueChange = { brand = it },
            label = { Text("Марка авто (например, LADA (VAZ))") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            singleLine = true

        )

        // 2. Поле для ввода модели
        OutlinedTextField(
            value = mark,
            onValueChange = { mark = it },
            label = { Text("Модель (например, 2108)") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            singleLine = true

        )

        // 3. Поле для ввода пробега (только цифры)
        OutlinedTextField(
            value = milliage,
            onValueChange = { milliage = it },
            label = { Text("Пробег (км)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            singleLine = true
        )

        // 4. Поле для ввода максимальной скорости (только цифры)
        OutlinedTextField(
            value = topSpeed,
            onValueChange = { topSpeed = it },
            label = { Text("Макс. скорость (км/ч)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            singleLine = true

        )

        Spacer(modifier = Modifier.weight(1f))

        // Проверяем, что пользователь заполнил все текстовые поля
        val isFormValid = brand.isNotBlank() && mark.isNotBlank() && milliage.isNotBlank() && topSpeed.isNotBlank()

        Button(
            onClick = {
                val milliageInt = milliage.toIntOrNull() ?: 0
                val topSpeedInt = topSpeed.toIntOrNull() ?: 0

                // Собираем объект машины
                val userCar = Car(
                    brand = brand,
                    mark = mark,
                    milliage = milliageInt,
                    topSpeed = topSpeedInt,
                    oil = FuelType.AI_92// Топливо по умолчанию
                )

                // Передаем созданную машину в MainActivity для сохранения
                onCarCreated(userCar)
            },
            enabled = isFormValid, // Кнопка нажимается только если форма заполнена
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Text(text = "Создать и перейти к машине", fontSize = 18.sp)
        }
    }
}
