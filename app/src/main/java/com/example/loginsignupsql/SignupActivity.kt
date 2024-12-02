package com.example.loginsignupsql

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.loginsignupsql.databinding.ActivitySignupBinding

// `SignupActivity` klasa predstavlja aktivnost za registraciju korisnika
class SignupActivity : AppCompatActivity() {

    // `binding` omogućava pristup elementima korisničkog interfejsa (UI) iz `activity_signup.xml`
    private lateinit var binding: ActivitySignupBinding

    // `databaseHelper` je instanca klase za rad sa SQLite bazom podataka
    private lateinit var databaseHelper: DatabaseHelper

    // `onCreate` metoda se poziva kada se aktivnost kreira
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Kreiranje `binding` instance za pristup UI elementima
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root) // Postavlja `binding.root` kao prikaz za aktivnost

        // Inicijalizacija `databaseHelper` objekta za rad sa bazom podataka
        databaseHelper = DatabaseHelper(this)

        // Postavljanje `OnClickListener` za dugme za registraciju
        binding.signupButton.setOnClickListener {
            // Dohvata unos korisnika za korisničko ime i lozinku sa UI elemenata
            val signupUsername = binding.signupUsername.text.toString()
            val signupPassword = binding.signupPassword.text.toString()
            // Poziva `signupDatabase` metodu za unos korisnika u bazu
            signupDatabase(signupUsername, signupPassword)
        }

        // Postavljanje `OnClickListener` za preusmeravanje na `LoginActivity`
        binding.loginRedirect.setOnClickListener {
            // Kreira novi `Intent` za prelazak na `LoginActivity`
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent) // Pokreće `LoginActivity`
            finish() // Završava `SignupActivity`
        }
    }

    // `signupDatabase` metoda unosi korisničke podatke u bazu
    private fun signupDatabase(username: String, password: String) {
        // Poziva `insertUser` metodu `databaseHelper` objekta koja vraća ID unosa
        val insertedRowId = databaseHelper.insertUser(username, password)
        if (insertedRowId != -1L) {
            // Ako je unos uspešan, prikazuje poruku "Signup Successful"
            Toast.makeText(this, "Signup Successful", Toast.LENGTH_SHORT).show()
            // Kreira `Intent` za prelazak na `LoginActivity`
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent) // Pokreće `LoginActivity`
            finish() // Završava `SignupActivity`
        } else {
            // Ako unos nije uspešan, prikazuje poruku "Signup Failed"
            Toast.makeText(this, "Signup Failed", Toast.LENGTH_SHORT).show()
        }
    }
}
