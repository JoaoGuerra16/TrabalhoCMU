import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@RoomDatabase(
        entities = [User::class, Ride::class],
        version = 1,
        exportSchema = false
)
@TypeConverters(PassengerListConverter::class)
abstract class BD : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun rideDao(): RideDao
}
