package com.example.loginsignupauth


/*Увоз потребних Android компоненти као што су Intent(за прелазак са једне активности на другу),
 Bundle (за чување и управљање подацима приликом креирања активности),
  и Toast (за кратке текстуалне обавештења).*/

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity //Наслеђивање основне класе за активности које користе подршку за модерне Android компоненте.
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.loginsignupauth.databinding.ActivityLoginBinding
/*View Binding аутоматски генерише класу за сваки XML layout фајл у апликацији. Ова класа садржи референце на све елементе корисничког интерфејса
(UI) у том layout-у. У овом случају, ActivityLoginBinding је генерисана класа на основу XML layout фајла који се зове activity_login.xml*/
import com.google.firebase.auth.FirebaseAuth //Увоз Firebase-а за аутентикацију корисника.

class LoginActivity : AppCompatActivity() { /*Ова класа наслеђује AppCompatActivity, што значи да је то активност у Android апликацији.
 Главна улога ове активности је омогућавање корисницима да унесу своје податке за пријављивање (email и лозинку).*/

    private lateinit var binding: ActivityLoginBinding /* Ова променљива омогућава коришћење View Binding-а,
     што значи да се коришћењем објекта binding приступа свим UI компонентама (као што су текстуална поља, дугмићи) дефинисаним у XML-у*/
    private lateinit var firebaseAuth: FirebaseAuth// Ово је инстанца Firebase-а за аутентикацију, коришћена за управљање корисничким налозима.

    override fun onCreate(savedInstanceState: Bundle?) { //Ово је почетна метода која се позива када активност почиње.
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater) //Овим се инициализује binding користећи инфлатер који генерише UI елементе дефинисане у XML фајлу.
        setContentView(binding.root) //Поставља главни изглед активности са свим визуелним компонентама.

        firebaseAuth = FirebaseAuth.getInstance() //Овде се добија инстанца Firebase-а за аутентикацију коју ће апликација користити за пријаву корисника.

        binding.loginButton.setOnClickListener{ //Када корисник кликне на дугме за пријаву, извршава се овај код.
            //Преузима се текст (email и лозинка) који је корисник унео.
            val email = binding.loginEmail.text.toString()
            val password = binding.loginPassword.text.toString()

            if(email.isNotEmpty() && password.isNotEmpty()){ // Проверава да ли су поља за емаил и лозинку попуњена.
                firebaseAuth.signInWithEmailAndPassword(email, password) //Позива се метода Firebase-а за пријаву корисника са емаилом и лозинком.
                    .addOnCompleteListener(this){task -> //Асинхрони метод који чека да се пријава заврши. Ако је пријава успешна, прелази се на MainActivity. У супротном, приказује се порука о грешци.
                        if (task.isSuccessful){
                            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show() //Ако је пријава успешна, приказује се кратка порука "Login Successful".
                            val intent = Intent(this, MainActivity::class.java) //Креира се Intent који омогућава прелазак на MainActivity.
                            startActivity(intent)
                            finish() //Завршава тренутну активност како би се спречило враћање назад на екран за пријаву.
                        } else {
                            Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show() //Ако подаци нису исправни, приказује се порука о грешци "Login Failed".
                        }
                    }
            } else {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }
        binding.signupText.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
            finish()
            /*ако корисник кликне на текст који позива на регистрацију, апликација се пребацује на SignupActivity
             (екран за регистрацију) користећи Intent.*/
        }
    }
}