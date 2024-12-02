package com.example.crudrealtimeclient
// Definiše paket u kojem se nalazi ova klasa, što omogućava organizaciju i lakšu upotrebu unutar projekta.

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.crudrealtimeclient.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
// Importi omogućavaju korišćenje potrebnih klasa i funkcionalnosti iz Android SDK-a, Firebase-a i drugih biblioteka.

class MainActivity : AppCompatActivity() {
    // Deklariše glavnu aktivnost aplikacije, koja nasleđuje funkcionalnost klase AppCompatActivity.

    /*1*/ private lateinit var binding: ActivityMainBinding
    // `binding` je kasnije inicijalizovani (lateinit) objekat koji povezuje XML layout (`activity_main.xml`) s Kotlin kodom pomoću View Binding-a.

    /*2*/ private lateinit var databaseReference: DatabaseReference
    // `databaseReference` je kasnije inicijalizovani objekat koji omogućava komunikaciju s Firebase Realtime Database-om.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Poziva osnovnu implementaciju `onCreate` metode iz klase `AppCompatActivity`.

        setContentView(binding.root)
        // Postavlja korisnički interfejs za ovu aktivnost na osnovu korenskog pogleda (`root`) iz `binding` objekta.

        /*4.*/ binding.searchButton.setOnClickListener {
            // Postavlja `OnClickListener` za dugme `searchButton`.

            val searchVehicleNumber: String = binding.searchVehicleNumber.text.toString()
            // Dohvata unos korisnika iz tekstualnog polja `searchVehicleNumber` kao string.

            if (searchVehicleNumber.isNotEmpty()) {
                // Proverava da li unos nije prazan.
                readData(searchVehicleNumber)
                // Ako unos postoji, poziva funkciju `readData` s unetim brojem vozila.
            } else {
                Toast.makeText(this, "Please enter the vehicle number", Toast.LENGTH_SHORT).show()
                // Ako je unos prazan, prikazuje poruku korisniku.
            }
        }
    }

    /*3*/ private fun readData(vehicleNumber: String) {
        // Funkcija za čitanje podataka iz Firebase-a na osnovu unetog broja vozila.

        databaseReference = FirebaseDatabase.getInstance().getReference("Vehicle Information")
        // Inicijalizuje `databaseReference` s referencom na čvor "Vehicle Information" u Firebase Realtime Database.

        databaseReference.child(vehicleNumber).get().addOnSuccessListener {
            // Pokušava da dohvati podatke za određeni broj vozila (kao ključ u bazi).

            if (it.exists()) {
                // Proverava da li traženi čvor postoji u bazi.

                val ownerName = it.child("ownerName").value
                val vehicleBrand = it.child("vehicleBrand").value
                val vehicleRTO = it.child("vehicleRTO").value
                // Dohvata vrednosti iz pod-čvorova za ime vlasnika, marku i registarski ured.

                Toast.makeText(this, "Results Found", Toast.LENGTH_SHORT).show()
                // Prikazuje poruku da su rezultati pronađeni.

                binding.searchVehicleNumber.text.clear()
                // Čisti unos korisnika nakon pretrage.

                binding.readOwnerName.text = ownerName.toString()
                binding.readVehicleBrand.text = vehicleBrand.toString()
                binding.readVehicleRTO.text = vehicleRTO.toString()
                // Postavlja dobijene podatke u odgovarajuća tekstualna polja u korisničkom interfejsu.
            } else {
                Toast.makeText(this, "Vehicle number does not exists", Toast.LENGTH_SHORT).show()
                // Ako traženi broj vozila ne postoji, prikazuje odgovarajuću poruku.
            }
        }.addOnFailureListener {
            // Ako dođe do greške pri čitanju podataka, izvršava se ovaj blok.

            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            // Prikazuje poruku o grešci.
        }
    }
}
