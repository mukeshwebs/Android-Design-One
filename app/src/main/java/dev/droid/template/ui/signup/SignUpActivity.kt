package dev.droid.template.ui.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dev.droid.template.ApplicationUtil.Companion.isEmailValid
import dev.droid.template.R
import dev.droid.template.databinding.ActivitySignUpBinding
import dev.droid.template.models.Users
import dev.droid.template.ui.login.LoginActivity

class SignUpActivity : AppCompatActivity() {
    lateinit var binding : ActivitySignUpBinding
    lateinit var database : DatabaseReference
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.btnSignUp.setOnClickListener {
            saveUserData()
        }

        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun saveUserData() {
        val name = binding.etName.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        if (name == "") {
            binding.tlName.isErrorEnabled = true
            binding.tlName.error = getString(R.string.please_enter_name)
        } else if (email == "") {
            binding.tlName.isErrorEnabled = false
            binding.tlEmail.isErrorEnabled = true
            binding.tlEmail.error = getString(R.string.please_enter_email_address)
        } else if (!email
                    .isEmailValid()
        ) {
            binding.tlEmail.isErrorEnabled = true
            binding.tlEmail.error = getString(R.string.please_enter_valid_email_address)
        } else if (password == "") {
            binding.tlEmail.isErrorEnabled = false
            binding.tlPassword.isErrorEnabled = true
            binding.tlPassword.error = getString(R.string.please_enter_password)
        } else {
            binding.tlPassword.isErrorEnabled = false

            database = FirebaseDatabase.getInstance("https://templete-1b88d-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users")
            val user = Users(name, email, password)
            database.child(name).setValue(user).addOnSuccessListener {
                binding.etName.text?.clear()
                binding.etEmail.text?.clear()
                binding.etPassword.text?.clear()
                Toast.makeText(this, "User registered successfully", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }.addOnFailureListener {
                Toast.makeText(this, "Error occurred user not register", Toast.LENGTH_SHORT).show()
            }
        }
    }
}