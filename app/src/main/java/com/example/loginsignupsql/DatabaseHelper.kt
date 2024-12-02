package com.example.loginsignupsql

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// Definisanje klase `DatabaseHelper` koja nasleđuje `SQLiteOpenHelper` za rad sa SQLite bazom
class DatabaseHelper(private val context: Context):
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // Companion object sadrži konstante koje koristimo u bazi podataka
    companion object {
        private const val DATABASE_NAME = "UserDatabase.db" // Naziv baze podataka
        private const val DATABASE_VERSION = 1 // Verzija baze podataka
        private const val TABLE_NAME = "data" // Naziv tabele za korisničke podatke
        private const val COLUMN_ID = "id" // Kolona za jedinstveni ID korisnika
        private const val COLUMN_USERNAME = "username" // Kolona za korisničko ime
        private const val COLUMN_PASSWORD = "password" // Kolona za lozinku
    }

    // `onCreate` se poziva kada baza prvi put bude kreirana
    override fun onCreate(db: SQLiteDatabase?) {
        // SQL upit za kreiranje tabele sa kolonama id, username i password
        val createTableQuery = ("CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " + // Kolona za automatski inkrementirajući primarni ključ
                "$COLUMN_USERNAME TEXT, " + // Kolona za tekst korisničkog imena
                "$COLUMN_PASSWORD TEXT)") // Kolona za tekst lozinke
        db?.execSQL(createTableQuery) // Izvršavanje SQL naredbe za kreiranje tabele
    }

    // `onUpgrade` se poziva kada se verzija baze podataka promeni
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // SQL upit za brisanje tabele ako ona postoji
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery) // Izvršavanje SQL naredbe za brisanje tabele
        onCreate(db) // Ponovno kreiranje tabele pozivanjem `onCreate` metode
    }

    // Metoda za unos novog korisnika u bazu podataka
    fun insertUser(username: String, password: String): Long {
        // Kreiranje `ContentValues` objekta za čuvanje korisničkog imena i lozinke
        val values = ContentValues().apply {
            put(COLUMN_USERNAME, username) // Postavljanje korisničkog imena
            put(COLUMN_PASSWORD, password) // Postavljanje lozinke
        }
        val db = writableDatabase // Otvaranje baze u režimu pisanja
        return db.insert(TABLE_NAME, null, values) // Unos podataka u tabelu i vraćanje ID-a unosa
    }

    // Metoda za proveru postojanja korisnika na osnovu korisničkog imena i lozinke
    fun readUser(username: String, password: String): Boolean {
        val db = readableDatabase // Otvaranje baze u režimu čitanja
        // Definisanje kriterijuma pretrage (WHERE uslov) za korisničko ime i lozinku
        val selection = "$COLUMN_USERNAME = ? AND $COLUMN_PASSWORD = ?"
        val selectionArgs = arrayOf(username, password) // Argumenti pretrage
        // Izvršavanje upita nad bazom podataka
        val cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null)

        // Provera da li postoji barem jedan rezultat (ako da, korisnik postoji)
        val userExists = cursor.count > 0
        cursor.close() // Zatvaranje kursora nakon pretrage
        return userExists // Vraća true ako korisnik postoji, inače false
    }
}
