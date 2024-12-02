package com.example.crudrealtimeadmin

// Data klasa za čuvanje informacija o vozilu
data class VehicleData(
    // Ime vlasnika vozila. Nullable, jer možda nije dostupno pri kreiranju objekta.
    val ownerName: String? = null,

    // Marka vozila. Nullable, jer informacija možda nije obavezna.
    val vehicleBrand: String? = null,

    // Registarski ured (RTO - Regional Transport Office) gde je vozilo registrovano.
    // Nullable, jer ta informacija možda nije uvek relevantna.
    val vehicleRTO: String? = null,

    // Registarski broj vozila. Nullable, jer vozilo možda još nije registrovano.
    val vehicleNumber: String? = null
) {
    // Klasa trenutno nema dodatnih metoda ili logike.
    // Samo služi kao model podataka za CRUD operacije.
}
