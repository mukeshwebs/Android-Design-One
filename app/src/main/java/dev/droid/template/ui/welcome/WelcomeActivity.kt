package dev.droid.template.ui.welcome

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import dev.droid.template.R
import dev.droid.template.databinding.ActivityWelcomeBinding
import dev.droid.template.ui.login.LoginActivity
import dev.droid.template.ui.signup.SignUpActivity

class WelcomeActivity : AppCompatActivity() {
    lateinit var binding : ActivityWelcomeBinding
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome)

        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.btnCreateAc.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}