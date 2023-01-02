package dev.droid.template.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.database.*
import dev.droid.template.DashboardActivity
import dev.droid.template.R
import dev.droid.template.databinding.ActivityLoginBinding
import dev.droid.template.ui.signup.SignUpActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.ivBack.setOnClickListener {
            finish()
        }
        database = FirebaseDatabase.getInstance("https://templete-1b88d-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("Users")
        binding.btnLogin.setOnClickListener {
            if (binding.etName.text.toString() == "") {
                binding.tlName.isErrorEnabled = true
                binding.tlName.error = getString(R.string.please_enter_name)
            } else if (binding.etPassword.text.toString() == "") {
                binding.tlName.isErrorEnabled = false
                binding.tlPassword.isErrorEnabled = true
                binding.tlPassword.error = getString(R.string.please_enter_password)
            } else {
                binding.tlPassword.isErrorEnabled = false
                database.child(binding.etName.text.toString())
                        .addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(snapshot : DataSnapshot) {
                                if (snapshot.exists()) {
                                    if (snapshot.child("password").value!! == binding.etPassword.text.toString()) {
                                        Toast.makeText(this@LoginActivity, "User login successfully", Toast.LENGTH_SHORT)
                                                .show()
                                        startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
                                        finish()
                                    } else {
                                        Toast.makeText(this@LoginActivity, "Password doesn't match", Toast.LENGTH_SHORT)
                                                .show()
                                        startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
                                        finish()
                                    }
                                } else {
                                    Toast.makeText(this@LoginActivity, "User not exists", Toast.LENGTH_SHORT)
                                            .show()
                                }
                            }

                            override fun onCancelled(databaseError : DatabaseError) {
                                Log.d("Error", databaseError.message)
                            }
                        })
            }
        }

        binding.tvSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }
    }

    private fun String.isEmailValid() : Boolean {
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this)
                .matches()
    }
}