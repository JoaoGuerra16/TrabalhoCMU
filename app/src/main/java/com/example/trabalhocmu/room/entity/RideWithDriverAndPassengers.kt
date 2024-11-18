data class RideWithDriverAndPassengers(
    val ride: Ride,
    val driver: User, // Usuário que criou a carona
    val passengers: List<User> // Usuários que são passageiros da carona
)
