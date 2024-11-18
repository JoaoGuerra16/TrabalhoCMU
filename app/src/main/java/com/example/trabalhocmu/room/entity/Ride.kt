import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "rides")
data class Ride(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val driverId: Int, // ID do motorista (referência ao User)
    val startingPoint: String,
    val finalDestination: String,
    val startingDate: String,
    val executedArrival: String,
    val availablePlaces: Int,
    @TypeConverters(PassengerListConverter::class) // Conversor para lista de IDs
    val passengers: List<Int>, // Lista de IDs dos passageiros (User)
    val petsAllowed: Boolean,
    val baggageAllowed: Boolean,
    val smokingAllowed: Boolean
)
