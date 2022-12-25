package dev.droid.template

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import dev.droid.template.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    lateinit var binding : ActivitySignUpBinding
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.btnSignUp.setOnClickListener {
            if (binding.etName.text.toString() == "") {
                binding.tlName.isErrorEnabled = true
                binding.tlName.error = getString(R.string.please_enter_name)
            } else if (binding.etEmail.text.toString() == "") {
                binding.tlName.isErrorEnabled = false
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

        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun String.isEmailValid() : Boolean {
        return ! TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this)
                .matches()
    }
}