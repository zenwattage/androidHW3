package com.ad340.hw6App

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ad340.hw6App.databinding.ActivityMainBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.UserProfileChangeRequest

class MainActivity : AppCompatActivity(){

     private lateinit var binding: ActivityMainBinding
     private lateinit var usernameInput: TextInputEditText
     private lateinit var emailInput: TextInputEditText
     private lateinit var passwordInput: TextInputEditText

     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)

         binding = ActivityMainBinding.inflate(layoutInflater)
         setContentView(R.layout.activity_main)

         val loginBtn = findViewById<Button>(R.id.loginBtn)
         val movieBtn = findViewById<Button>(R.id.movieBtn)
         val mapBtn = findViewById<Button>(R.id.mapBtn)
         val trafficBtn = findViewById<Button>(R.id.trafficBtn)

         trafficBtn.setOnClickListener {
//             Toast.makeText(this, "Hi there! This is a ${this.getString(R.string.nwBtn)} Button!", Toast.LENGTH_LONG).show()
             val intent = Intent(this, TrafficCameras::class.java)
             startActivity(intent)
         }

         movieBtn.setOnClickListener {
//             Toast.makeText(this, "Hi there! This is the ${this.getString(R.string.movieBtn)} Button!", Toast.LENGTH_LONG).show()
             val intent = Intent(this, Movies::class.java)
             startActivity(intent)
         }

         loginBtn.setOnClickListener {
             Toast.makeText(this, "Logging in...", Toast.LENGTH_LONG).show()
             if (isFormValid()) {
                 Toast.makeText(this, "Sign-in valid!", Toast.LENGTH_LONG).show()
                 signIn()
             } else {
                 signIn()
                 Toast.makeText(this, "Invalid Sign-in.", Toast.LENGTH_LONG).show()

             }
         }


         mapBtn.setOnClickListener {
//             Toast.makeText(this, "this is the ${this.getString(R.string.mapBtn)}", Toast.LENGTH_LONG).show()
             val intent = Intent(this, Map::class.java)
             startActivity(intent)
         }

         usernameInput = binding.textInputUsername
         emailInput = binding.textInputEmail
         passwordInput = binding.textInputPassword

         val savedInfo = readFromSharedPreference()

         emailInput.setText(savedInfo[0])
         passwordInput.setText(savedInfo[1])
         usernameInput.setText(savedInfo[2])

     }

      //form validation
      private fun isFormValid(): Boolean {
          val validEmail = validateEmail(emailInput.text.toString().trim())
          val validUsername =
              validateBlankString(usernameInput.text.toString().trim(), usernameInput)
          val validPassword =
              validateBlankString(passwordInput.text.toString().trim(), passwordInput)

          return (validEmail && validUsername && validPassword)
      }

      private fun signIn() {
          val mAuth = FirebaseAuth.getInstance()
          mAuth.signInWithEmailAndPassword(
              emailInput.text.toString(),
              passwordInput.text.toString()
          )
              .addOnCompleteListener(this) { task -> Log.d("FIREBASE", "Sign In :" + task.isSuccessful)
                  if (task.isSuccessful) {
                      writeToSharedPreference(
                          emailInput.text.toString(),
                          passwordInput.text.toString(),
                          usernameInput.text.toString()
                      )
                      // update profile
                      val user = FirebaseAuth.getInstance().currentUser
                      val profileUpdates = UserProfileChangeRequest.Builder()
                          .setDisplayName(usernameInput.text.toString())
                          .build()
                      user!!.updateProfile(profileUpdates)
                          .addOnCompleteListener { task ->
                              if (task.isSuccessful) {
                                  Log.d("FIREBASE", "User profile updated.")
                                  // Go to FirebaseActivity
                                  startActivity(Intent(this, FirebaseActivity::class.java))
                              }
                          }
                  } else {
                      Log.e("FIREBASE", "sign-in failed")
                      Log.e("task", task.exception.toString())
                      displaySignInError(task.exception)
                  }
              }
      }

      private fun validateEmail(email: String): Boolean {
          if (email.isBlank()) {
              emailInput.error = "Email cannot be blank."
              return false
          } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
              emailInput.error = "Please enter a valid Email address."
              return false
          } else {
              emailInput.error = null
              return true
          }
      }

      private fun validateBlankString(input: String, textInput: TextInputEditText): Boolean {
          if (input.isBlank()) {
              textInput.error = "${textInput.hint} cannot be blank."
              return false
          } else {
              textInput.error = null
              return true
          }
      }


      private fun writeToSharedPreference(email: String, password: String, username: String) {
          val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
          with(sharedPref.edit()) {
              putString("email", email)
              putString("password", password)
              putString("username", username)
              apply()
          }
      }

      private fun readFromSharedPreference(): List<String> {
          val sharedPref = this.getPreferences(Context.MODE_PRIVATE)
          val savedInfo: MutableList<String> = mutableListOf()
            savedInfo.add(sharedPref.getString("email", "schoolandmountains@gmail.com").toString())
            savedInfo.add(sharedPref.getString("password", "9MS524LGdS").toString())
            savedInfo.add(sharedPref.getString("username", "Scott").toString())

          return savedInfo
      }

      private fun displaySignInError(e: Exception?) {
          when (e) {
              is FirebaseNetworkException -> Toast.makeText(
                  this,
                  "Network error.",
                  Toast.LENGTH_SHORT
              ).show()
              is FirebaseAuthInvalidUserException -> Toast.makeText(
                  this,
                  "Unknown user.",
                  Toast.LENGTH_SHORT
              ).show()
              is FirebaseAuthInvalidCredentialsException -> Toast.makeText(
                  this,
                  "Incorrect password.",
                  Toast.LENGTH_SHORT
              ).show()
              else -> Toast.makeText(this, "Unknown error: ${e?.message}", Toast.LENGTH_SHORT).show()
          }
      }


  }
