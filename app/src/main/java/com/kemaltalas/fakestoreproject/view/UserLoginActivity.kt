package com.kemaltalas.fakestoreproject.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.kemaltalas.fakestoreproject.R
import kotlinx.android.synthetic.main.activity_user_login.*

class UserLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_login)

        backButton.setOnClickListener{
            onBackPressed()
        }


        loginButton.setOnClickListener {

                Toast.makeText(this,"Başarıyla Giriş Yapıldı",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,MainActivity::class.java))
        }

    }
}