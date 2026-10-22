package com.pgkchamps.lessoncompose

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.pgkchamps.lessoncompose.data.Car
import com.pgkchamps.lessoncompose.data.FuelType
import com.pgkchamps.lessoncompose.presentation.screens.HomeScreen
import com.pgkchamps.lessoncompose.presentation.screens.TestScreen
import com.pgkchamps.lessoncompose.ui.theme.LessonComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val prefs = getSharedPreferences("CarPrefs", Context.MODE_PRIVATE)

        val savedBrand = prefs.getString("car_brand", null)
        val initialCar = if (savedBrand != null) {
            Car(
                brand = savedBrand,
                mark = prefs.getString("car_mark", "") ?: "",
                milliage = prefs.getInt("car_milliage", 0),
                topSpeed = prefs.getInt("car_topspeed", 0),
                oil = FuelType.valueOf(prefs.getString("car_fuel", FuelType.AI_92.name) ?: FuelType.AI_92.name)
            )
        } else {
            null
        }

        setContent {

            LessonComposeTheme {
                var myCar by remember { mutableStateOf<Car?>(initialCar) }
                var currentScreen by remember { mutableStateOf(if (initialCar != null) "test" else "home") }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    Box(modifier = Modifier.padding(innerPadding))

                    if (currentScreen == "home") {
                        HomeScreen(onCarCreated = { createdCar ->
                            myCar = createdCar

                            prefs.edit().apply {
                                putString("car_brand", createdCar.brand)
                                putString("car_mark", createdCar.mark)
                                putInt("car_milliage", createdCar.milliage)
                                putInt("car_topspeed", createdCar.topSpeed)
                                putString("car_fuel", createdCar.oil.name)
                                apply()
                            }

                            currentScreen = "test"
                        })
                    } else {
                        myCar?.let { car ->
                            TestScreen(
                                car = car,
                                onFuelChanged = { newFuel ->
                                    prefs.edit().putString("car_fuel", newFuel.name).apply()
                                },
                                onResetClicked = {
                                    // 1. Очищаем все данные в памяти SharedPreferences
                                    prefs.edit().clear().apply()
                                    // 2. Стираем текущую сохраненную машину из оперативной памяти
                                    myCar = null
                                    // 3. Возвращаем пользователя на экран ввода (HomeScreen)
                                    currentScreen = "home"
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
