import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PassengerListConverter {
    private val gson = Gson()

    // Converte uma lista de IDs para String (JSON)
    @TypeConverter
    fun fromPassengerList(passengers: List<Int>?): String {
        return gson.toJson(passengers)
    }

    // Converte uma String (JSON) de volta para uma lista de IDs
    @TypeConverter
    fun toPassengerList(data: String?): List<Int> {
        val listType = object : TypeToken<List<Int>>() {}.type
        return gson.fromJson(data, listType)
    }
}
