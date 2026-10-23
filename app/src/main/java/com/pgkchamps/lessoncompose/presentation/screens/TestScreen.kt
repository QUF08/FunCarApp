package com.pgkchamps.lessoncompose.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pgkchamps.lessoncompose.data.Car
import com.pgkchamps.lessoncompose.data.FuelType
import com.pgkchamps.lessoncompose.data.addDownhillSpeed
import com.pgkchamps.lessoncompose.data.resetStraightSpeed

@Composable
fun TestScreen(
    car: Car,
    onFuelChanged: (FuelType) -> Unit,
    onResetClicked: () -> Unit // <-- Новое событие для кнопки сброса
) {
    var isDownhill by remember { mutableStateOf(false) }
    var currentCar by remember { mutableStateOf(car) }
    var selectedFuel by remember { mutableStateOf(car.oil) }
    var isMenuExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 64.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Характеристики машины
        Text(text = "Марка: ${car.brand}", fontSize = 20.sp)
        Text(text = "Модель: ${car.mark}", fontSize = 20.sp)
        Text(text = "Пробег: ${car.milliage} км", fontSize = 20.sp)
        Text(text = "Макс. скорость: ${currentCar.topSpeed} км/ч", fontSize = 20.sp)

        Button(
            onClick = {
                if (!isDownhill) {
                    // Если еще не на горке: прибавляем 20 и переключаем флаг
                    currentCar = currentCar.addDownhillSpeed()
                    isDownhill = true
                } else {
                    // Если уже с горки: убавляем 20 и возвращаем флаг обратно
                    currentCar = currentCar.resetStraightSpeed()
                    isDownhill = false
                }
            }
        ) {
            Text(
                text = if (isDownhill) {
                    "Скорость по прямой (-20 км/ч)"
                } else {
                    "Скорость с горки (+20 км/ч)"
                }
            )
        }
        // Выбор топлива
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp)
        ) {
            OutlinedTextField(
                value = selectedFuel.displayName,
                onValueChange = {},
                readOnly = true,
                label = { Text("Тип топлива (Нажми для выбора)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isMenuExpanded = true },
                enabled = false
            )

            DropdownMenu(
                expanded = isMenuExpanded,
                onDismissRequest = { isMenuExpanded = false }
            ) {
                FuelType.values().forEach { fuel ->
                    DropdownMenuItem(
                        text = { Text(text = fuel.displayName) },
                        onClick = {
                            selectedFuel = fuel
                            car.oil = fuel
                            isMenuExpanded = false
                            onFuelChanged(fuel)
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.size(32.dp))
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = Color.Gray)

        Text(
            text = "Результаты диагностики:",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Логика пробега
        if (car.milliage > 100000) {
            Text(
                text = "⚠️ Внимание: пробег больше 100 000 км! Могут быть проблемы с технической составляющей автомобиля.",
                fontSize = 15.sp,
                color = Color.Yellow,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        // Логика топлива
        val fuelVerdict = when (selectedFuel) {
            FuelType.AI_95, FuelType.AI_100 -> "!! Будут проблемы с заправкой !!"
            FuelType.AI_92 -> "✅ Проблем с заправкой не будет."
            FuelType.DT -> "✅ Дизель — проблем с заправкой не будет."
        }

        val fuelColor = if (selectedFuel == FuelType.AI_95 || selectedFuel == FuelType.AI_100) {
            Color.Red
        } else {
            Color.Green
        }

        Text(
            text = fuelVerdict,
            fontSize = 15.sp,
            color = fuelColor,
            fontWeight = FontWeight.Medium
        )

        // Этот Spacer вытолкнет кнопку «Сброс» в самый низ экрана
        Spacer(modifier = Modifier.weight(1f))

        // Кнопка сброса (сделаем её тревожного красного цвета для стиля)
        Button(
            onClick = onResetClicked,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        ) {
            Text(text = "Сбросить данные", fontSize = 18.sp, color = Color.White)
        }
    }
}
