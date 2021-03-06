import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt
import java.io.File

@JsonClass(generateAdapter = true)
data class Temperature(
    @Json(name = "main")
    val main: Main
)

@JsonClass(generateAdapter = true)
data class Main(
    @Json(name = "temp")
    val temp: Double
)

fun parseJsonString(jsonData: String?): Double? {
    val moshi = Moshi.Builder()
        .build()
    val adapter = moshi.adapter(Temperature::class.java)
    val temperatureKelvin = adapter.fromJson(jsonData)
    return temperatureKelvin?.main?.temp?.minus(273)
}

fun getResponse(lat: String, lon: String): String? {
    val client = OkHttpClient.Builder()
        .callTimeout(5, TimeUnit.SECONDS)
        .build()
    val apiKey = System.getenv("WEATHER_API_KEY")
    val url = "https://api.openweathermap.org/data/2.5/weather?lat=$lat&lon=$lon&appid=$apiKey"
    val request = Request.Builder()
        .url(url)
        .build()
     client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) {
            throw IOException("Connection error $response")
        }
        val jsonData = response.body?.string()
        return jsonData
    }
}

fun main(args: Array<String>) {
    if(args.size == 2 && args[0] == "-c") {
        val coord = args[1].split(",")
        if(coord.size == 2 && coord[0].toDoubleOrNull() != null && coord[1].toDoubleOrNull() != null) {
            val response = getResponse(coord[0], coord[1])
            val temperature = parseJsonString(response)
            println(temperature?.roundToInt())
        } else {
            println("Input latitude and longitude")
        }
    } else if(args.size == 4 && args[0] == "-c" && args[2] == "-f") {
        val coord = args[1].split(",")
        val response = getResponse(coord[0], coord[1])
        val temperature = parseJsonString(response)
        if(coord.size == 2 && coord[0].toDoubleOrNull() != null && coord[1].toDoubleOrNull() != null) {
            val file = File(args[3])
            if(file.exists()) {
                if(file.canWrite()) {
                    file.writeText("${temperature?.roundToInt()}")
                    println("File created")
                } else {
                    println("File can't be a written")
                }
            } else {
                try {
                    file.createNewFile()
                    file.writeText("${temperature?.roundToInt()}")
                    println("File created")
                } catch (e: IOException) {
                    println("File creation error")
                }
            }
        } else {
            println("Input latitude and longitude")
        }
    } else {
        println("Use a template")
        println("-c LATITUDE,LONGITUDE [-f PATH]")
    }
}
