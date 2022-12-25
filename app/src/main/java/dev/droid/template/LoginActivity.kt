package dev.droid.template

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import dev.droid.template.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.btnLogin.setOnClickListener {
            if (binding.etEmail.text.toString() == "") {
                binding.tlEmail.isErrorEnabled = true
                binding.tlEmail.error = getString(R.string.please_enter_email_address)
            } else if (! binding.etEmail.text.toString()
                        .isEmailValid()
            ) {
                binding.tlEmail.isErrorEnabled = true
                binding.tlEmail.error = getString(R.string.please_enter_valid_email_address)
            } else if (binding.etPassword.text.toString() == "") {
                binding.tlEmail.isErrorEnabled = false
                binding.tlPassword.isErrorEnabled = true
                binding.tlPassword.error = getString(R.string.please_enter_password)
            } else {
                binding.tlPassword.isErrorEnabled = false
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }

        binding.tvSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }
    }

    private fun String.isEmailValid() : Boolean {
        return ! TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this)
                .matches()
    }
}