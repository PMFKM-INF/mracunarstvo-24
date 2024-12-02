package com.example.loginsignupauth

import android.content.Intent //Омогућава креирање намера (Intent) за навигацију између активности.
import android.os.Bundle //Користи се за прослеђивање података приликом креирања активности.
import android.widget.Toast //Омогућава приказивање кратких текстуалних порука кориснику.
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.loginsignupauth.databinding.ActivitySignupBinding//Овде је омогућено коришћење View Binding-а како би се поједноставио приступ UI елементима.
import com.google.firebase.auth.FirebaseAuth // Ово је Firebase-ова класа за рад са аутентификацијом корисника.

class SignupActivity : AppCompatActivity() { /*Класа SignupActivity је потомак класе AppCompatActivity,
 која обезбеђује функционалности за управљање активностима у Android-у. */

    private lateinit var binding: ActivitySignupBinding //Инстанца класе ActivitySignupBinding, која је генерисана од XML layout фајла
    private lateinit var  firebaseAuth: FirebaseAuth //Инстанца класе FirebaseAuth која управља аутентификацијом корисника.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater) //Овим се инициализује binding користећи инфлатер који генерише UI елементе дефинисане у XML фајлу.
        setContentView(binding.root) ////Поставља главни изглед активности са свим визуелним компонентама.


        firebaseAuth = FirebaseAuth.getInstance() //Овим се добија инстанца Firebase аутентификације која ће бити коришћена за креирање нових корисника.

        binding.signupButton.setOnClickListener{ //Када корисник кликне на дугме за пријаву, извршава се овај код.
            //Преузима се текст (email и лозинка) који је корисник унео.
            val email = binding.signupEmail.text.toString()
            val password = binding.signupPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()){ // Проверава да ли су поља за емаил и лозинку попуњена.
                firebaseAuth.createUserWithEmailAndPassword(email, password) //Позива се метода Firebase-а за регитрацију корисника са емаилом и лозинком.
                    .addOnCompleteListener(this) {task -> //Асинхрони метод који чека да се регистрација заврши. Ако је регистрација успешна, прелази се на LoginActivity. У супротном, приказује се порука о грешци.
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Signup Successful", Toast.LENGTH_SHORT).show() //Ако је регистрација успешна, приказује се кратка порука "Signup Successful".
                            val intent = Intent(this, LoginActivity::class.java) ////Креира се Intent који омогућава прелазак на LoginActivity.
                            startActivity(intent)
                            finish() //Завршава тренутну активност како би се спречило враћање назад на екран за регистрацију.
                        } else{
                            Toast.makeText(this, "Signup Unsuccessful", Toast.LENGTH_SHORT).show() //Ако подаци нису исправни, приказује се порука о грешци "Signup Unsuccessfull".

                        }
                    }
            } else {
                Toast.makeText(this, "Enter email and password", Toast.LENGTH_SHORT).show()

            }
        }

        binding.loginText.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            /*ако корисник кликне на текст који позива на пријаву, апликација се пребацује на LoginActivity
             (екран за пријаву) користећи Intent.*/
        }
    }
}