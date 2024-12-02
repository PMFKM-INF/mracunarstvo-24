package com.example.crudrealtimeadmin  // Definiše paket u kojem se nalazi ova klasa

import android.os.Bundle  // Importuje klasu Bundle koja se koristi za čuvanje podataka tokom kreiranja aktivnosti
import android.widget.Toast  // Importuje klasu Toast koja se koristi za prikazivanje kratkih poruka korisniku
import androidx.activity.enableEdgeToEdge  // Importuje funkciju za omogućavanje režima "edge-to-edge" (proširenje ekrana na ivice)
import androidx.appcompat.app.AppCompatActivity  // Importuje osnovnu aktivnost za korišćenje podrške za kompatibilnost sa starijim verzijama Androida
import androidx.core.view.ViewCompat  // Importuje klasu za rad sa pogledima (views) sa dodatnim funkcijama
import androidx.core.view.WindowInsetsCompat  // Importuje klasu koja omogućava rad sa prozorskim urezima (kao što su notch ili status bar)
import com.example.crudrealtimeadmin.databinding.ActivityUpdateBinding  // Importuje generisani binding za povezivanje sa XML fajlom aktivnosti
import com.google.firebase.database.DatabaseReference  // Importuje klasu koja omogućava rad sa referencama u Firebase bazi podataka
import com.google.firebase.database.FirebaseDatabase  // Importuje klasu koja omogućava pristup Firebase bazi podataka

class UpdateActivity : AppCompatActivity() {  // Definiše klasu UpdateActivity koja nasleđuje AppCompatActivity

    private lateinit var binding: ActivityUpdateBinding  // Deklariše promenljivu za binding koji povezuje UI sa kodom
    private lateinit var databaseReference: DatabaseReference  // Deklariše promenljivu za Firebase bazu podataka

    override fun onCreate(savedInstanceState: Bundle?) {  // Metoda koja se poziva pri kreiranju aktivnosti
        super.onCreate(savedInstanceState)  // Poziva metod iz roditeljske klase za osnovnu funkcionalnost
        binding = ActivityUpdateBinding.inflate(layoutInflater)  // Inicijalizuje binding sa XML fajlom
        setContentView(binding.root)  // Postavlja korenski pogled (root view) kao sadržaj aktivnosti

        // Postavlja listener za klik na dugme za ažuriranje podataka
        binding.updateButton.setOnClickListener{
            // Uzima podatke sa UI elemenata
            val referenceVehicleNumber = binding.referenceVehicleNumber.text.toString()  // Uzima broj vozila iz EditText-a
            val updateOwnerName = binding.updateOwnerName.text.toString()  // Uzima ime vlasnika iz EditText-a
            val updateVehicleBrand = binding.updateVehicleBrand.text.toString()  // Uzima marku vozila iz EditText-a
            val updateVehicleRTO = binding.updateVehicleRTO.text.toString()  // Uzima RTO iz EditText-a

            // Poziva funkciju za ažuriranje podataka
            updateData(referenceVehicleNumber, updateOwnerName, updateVehicleBrand, updateVehicleRTO)
        }
    }

    private fun updateData(vehicleNumber: String, ownerName: String, vehicleBrand: String, vehicleRTO: String){  // Funkcija koja ažurira podatke u bazi
        databaseReference = FirebaseDatabase.getInstance().getReference("Vehicle Information")  // Povezuje se sa Firebase bazom i dobija referencu na "Vehicle Information"
        // Kreira mapu sa novim podacima koji treba da budu ažurirani
        val vehicleData = mapOf<String, String>("ownerName" to ownerName, "vehicleBrand" to vehicleBrand, "vehicleRTO" to vehicleRTO)
        // Ažurira podatke za dati broj vozila
        databaseReference.child(vehicleNumber).updateChildren(vehicleData).addOnSuccessListener {  // Kada je ažuriranje uspešno
            // Čisti uneta polja u UI
            binding.referenceVehicleNumber.text.clear()
            binding.updateOwnerName.text.clear()
            binding.updateVehicleBrand.text.clear()
            binding.updateVehicleRTO.text.clear()
            // Prikazuje poruku korisniku da su podaci ažurirani
            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{  // U slučaju neuspeha ažuriranja
            // Prikazuje poruku korisniku da nije moguće ažurirati podatke
            Toast.makeText(this, "Unable to Update", Toast.LENGTH_SHORT).show()
        }
    }
}
