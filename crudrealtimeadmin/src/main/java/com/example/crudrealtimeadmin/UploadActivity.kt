package com.example.crudrealtimeadmin
// Definiše paket u kojem se nalazi klasa, koristi se za organizaciju koda i lakše referenciranje unutar projekta.

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.crudrealtimeadmin.databinding.ActivityUploadBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
// Importi potrebnih klasa i biblioteka za rad sa Firebase-om, korisničkim interfejsom i navigacijom.

class UploadActivity : AppCompatActivity() {
    // Deklariše klasu `UploadActivity` koja nasleđuje `AppCompatActivity` za rad sa aktivnostima u Androidu.

    /*1*/  private lateinit var binding: ActivityUploadBinding
    // `binding` omogućava povezivanje XML layout-a `activity_upload.xml` s kodom pomoću View Binding-a.

    /*2*/  private lateinit var databaseReference: DatabaseReference
    // `databaseReference` je kasnije inicijalizovani objekat za interakciju s Firebase Realtime Database-om.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Poziva osnovnu implementaciju `onCreate` metode iz `AppCompatActivity`.

        binding = ActivityUploadBinding.inflate(layoutInflater)
        // Inicijalizuje `binding` tako da može pristupati elementima korisničkog interfejsa.

        setContentView(binding.root)
        // Postavlja korenski prikaz aktivnosti koristeći `binding`.

        /*3*/  binding.saveButton.setOnClickListener {
            // Postavlja `OnClickListener` za dugme `saveButton`, koje će se aktivirati kada korisnik klikne na dugme.

            val ownerName = binding.uploadOwnerName.text.toString()
            val vehicleBrand = binding.uploadVehicleBrand.text.toString()
            val vehicleRTO = binding.uploadVehicleRTO.text.toString()
            val vehicleNumber = binding.uploadVehicleNumber.text.toString()
            // Dohvata unos korisnika iz tekstualnih polja: ime vlasnika, marka vozila, RTO i registarski broj vozila.

            databaseReference = FirebaseDatabase.getInstance().getReference("Vehicle Information")
            // Inicijalizuje referencu na Firebase čvor "Vehicle Information", gde će se čuvati podaci.

            val vehicleData = VehicleData(ownerName, vehicleBrand, vehicleRTO, vehicleNumber)
            // Kreira instancu klase `VehicleData` s podacima koje je korisnik uneo.

            databaseReference.child(vehicleNumber).setValue(vehicleData).addOnSuccessListener {
                // Postavlja podatke na čvor u bazi sa ključem `vehicleNumber` i definiše ponašanje pri uspehu.

                binding.uploadOwnerName.text.clear()
                binding.uploadVehicleBrand.text.clear()
                binding.uploadVehicleRTO.text.clear()
                binding.uploadVehicleNumber.text.clear()
                // Čisti tekstualna polja nakon uspešnog unosa podataka.

                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
                // Prikazuje poruku o uspehu korisniku.

                val intent = Intent(this@UploadActivity, MainActivity::class.java)
                // Kreira `Intent` za navigaciju ka `MainActivity`.

                startActivity(intent)
                // Pokreće `MainActivity`.

                finish()
                // Zatvara trenutnu aktivnost kako bi korisnik završio unos podataka.
            }.addOnFailureListener {
                // Definiše ponašanje u slučaju greške pri upisu podataka u bazu.

                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                // Prikazuje poruku o grešci korisniku.
            }
        }
    }
}
