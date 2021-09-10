package com.example.okhttpdoesntworkapplication


data class WeatherModel(
//	val id: String? = "",
//	val isActive: Boolean = false,
//	val fullName: String? = "",
//	val firstName: String? = null,
//	val middleName: String? = null,
//	val lastName: String? = null,
//	val email: String? = null,
//	val isEmailConfirmed: Boolean = false,
//	val phone: String? = null,
//	val isPhoneConfirmed: Boolean = false,
//	val activeCompanyId: String? = null


    val coord: Tmp1,
    val weather: List<Tmp2>,
    val base: String,
    val main: Tmp3,
    val visibility: Long
//    {
//        "coord": {
//        "lon": -0.13,
//        "lat": 51.51
//    },
//        "weather": [
//        {
//            "id": 300,
//            "main": "Drizzle",
//            "description": "light intensity drizzle",
//            "icon": "09d"
//        }
//        ],
//        "base": "stations",
//        "main": {
//        "temp": 280.32,
//        "pressure": 1012,
//        "humidity": 81,
//        "temp_min": 279.15,
//        "temp_max": 281.15
//    },
//        "visibility": 10000,
//        "wind": {
//        "speed": 4.1,
//        "deg": 80
//    },
//        "clouds": {
//        "all": 90
//    },
//        "dt": 1485789600,
//        "sys": {
//        "type": 1,
//        "id": 5091,
//        "message": 0.0103,
//        "country": "GB",
//        "sunrise": 1485762037,
//        "sunset": 1485794875
//    },
//        "id": 2643743,
//        "name": "London",
//        "cod": 200
//    }
)

class Tmp3 {

}

class Tmp2 {

}

class Tmp1 {

}
