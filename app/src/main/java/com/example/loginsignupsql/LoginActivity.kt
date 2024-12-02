package com.example.loginsignupsql

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.loginsignupsql.databinding.ActivityLoginBinding

// `LoginActivity` klasa predstavlja aktivnost za prijavu korisnika
class LoginActivity : AppCompatActivity() {

    // `binding` služi za povezivanje elemenata UI-a sa kodom, koristi `ActivityLoginBinding` za pristup elementima
    private lateinit var binding: ActivityLoginBinding

    // `databaseHelper` je objekat za rad sa bazom podataka
    private lateinit var databaseHelper: DatabaseHelper

    // `onCreate` metoda se poziva kada se aktivnost kreira
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflater kreira instancu `binding` za UI elemente iz `activity_login.xml` fajla
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root) // Postavlja `binding.root` kao prikaz za aktivnost

        // Inicijalizacija `databaseHelper` objekta za pristup bazi podataka
        databaseHelper = DatabaseHelper(this)

        // Postavljanje `OnClickListener` za dugme za prijavu
        binding.loginButton.setOnClickListener {
            // Dohvata tekst korisničkog imena i lozinke sa UI elemenata
            val loginUsername = binding.loginUsername.text.toString()
            val loginPassword = binding.loginPassword.text.toString()
            // Poziva `loginDatabase` metodu za proveru podataka u bazi
            loginDatabase(loginUsername, loginPassword)
        }

        // Postavljanje `OnClickListener` za preusmeravanje na `SignupActivity`
        binding.signupRedirect.setOnClickListener {
            // Kreira novi `Intent` za prelazak na `SignupActivity`
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent) // Pokreće `SignupActivity`
            finish() // Završava `LoginActivity` kako bi korisnik mogao da se vrati nazad nakon registracije
        }
    }

    // `loginDatabase` metoda proverava da li korisničko ime i lozinka postoje u bazi podataka
    private fun loginDatabase(username: String, password: String) {
        // Poziva `readUser` metodu `databaseHelper` objekta koja proverava postojanje korisnika
        val userExists = databaseHelper.readUser(username, password)
        if (userExists) {
            // Ako korisnik postoji, prikazuje poruku "Login Successful"
            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
            // Kreira `Intent` za prelazak na `MainActivity`
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent) // Pokreće `MainActivity`
            finish() // Završava `LoginActivity`
        } else {
            // Ako korisnik ne postoji, prikazuje poruku "Login Failed"
            Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
        }
    }
}
