package com.example.crudrealtimeadmin  // Definiše paket u kojem se nalazi ova klasa

import android.os.Bundle  // Importuje klasu Bundle koja se koristi za čuvanje podataka tokom kreiranja aktivnosti
import android.widget.Toast  // Importuje klasu Toast koja se koristi za prikazivanje kratkih poruka korisniku
import androidx.activity.enableEdgeToEdge  // Importuje funkciju za omogućavanje režima "edge-to-edge" (proširenje ekrana na ivice)
import androidx.appcompat.app.AppCompatActivity  // Importuje osnovnu aktivnost za korišćenje podrške za kompatibilnost sa starijim verzijama Androida
import androidx.core.view.ViewCompat  // Importuje klasu za rad sa pogledima (views) sa dodatnim funkcijama
import androidx.core.view.WindowInsetsCompat  // Importuje klasu koja omogućava rad sa prozorskim urezima (kao što su notch ili status bar)
import com.example.crudrealtimeadmin.databinding.ActivityDeleteBinding  // Importuje generisani binding za povezivanje sa XML fajlom aktivnosti
import com.google.firebase.database.DatabaseReference  // Importuje klasu koja omogućava rad sa referencama u Firebase bazi podataka
import com.google.firebase.database.FirebaseDatabase  // Importuje klasu koja omogućava pristup Firebase bazi podataka

class DeleteActivity : AppCompatActivity() {  // Definiše klasu DeleteActivity koja nasleđuje AppCompatActivity

    private lateinit var binding: ActivityDeleteBinding  // Deklariše promenljivu za binding koji povezuje UI sa kodom
    private lateinit var databaseReference: DatabaseReference  // Deklariše promenljivu za Firebase bazu podataka

    override fun onCreate(savedInstanceState: Bundle?) {  // Metoda koja se poziva pri kreiranju aktivnosti
        super.onCreate(savedInstanceState)  // Poziva metod iz roditeljske klase za osnovnu funkcionalnost
        binding = ActivityDeleteBinding.inflate(layoutInflater)  // Inicijalizuje binding sa XML fajlom
        setContentView(binding.root)  // Postavlja korenski pogled (root view) kao sadržaj aktivnosti

        // Postavlja listener za klik na dugme za brisanje
        binding.deleteButton.setOnClickListener {
            val vehicleNumber = binding.deleteVehicleNumber.text.toString()  // Uzima broj vozila iz EditText-a
            if (vehicleNumber.isNotEmpty()) {  // Proverava da li je broj vozila unet
                deleteData(vehicleNumber)  // Poziva funkciju za brisanje podataka ako je broj vozila unet
            } else {
                Toast.makeText(this, "Please enter vehicle number", Toast.LENGTH_SHORT).show()  // Prikazuje poruku ako broj vozila nije unet
            }
        }
    }

    private fun deleteData(vehicleNumber: String) {  // Funkcija koja briše podatke iz Firebase baze
        databaseReference = FirebaseDatabase.getInstance().getReference("Vehicle Information")  // Povezuje se sa Firebase bazom i dobija referencu na "Vehicle Information"
        databaseReference.child(vehicleNumber).removeValue().addOnSuccessListener {  // Briše podatke o vozilu sa datim brojem
            binding.deleteVehicleNumber.text.clear()  // Čisti uneti broj vozila u UI
            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()  // Prikazuje poruku da je podatak obrisan
        }.addOnFailureListener {  // U slučaju neuspeha brisanja
            Toast.makeText(this, "Unable to Delete", Toast.LENGTH_SHORT).show()  // Prikazuje poruku da nije bilo moguće obrisati podatke
        }
    }
}
