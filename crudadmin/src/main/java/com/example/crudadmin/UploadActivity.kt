package com.example.crudadmin

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.crudadmin.databinding.ActivityUploadBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UploadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUploadBinding
    private lateinit var databaseReference: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.saveButton.setOnClickListener { it: View ->

            val name = binding.uploadName.text.toString()
            val operator = binding.uploadOperator.text.toString()
            val location = binding.uploadLocation.text.toString()
            val phone = binding.uploadPhone.text.toString()

            databaseReference = FirebaseDatabase.getInstance().getReference("phone Directory")


            val phoneDirectory = HashMap<String, String>()
            phoneDirectory["name"] = name
            phoneDirectory["operator"] = operator
            phoneDirectory["location"] = location
            phoneDirectory["phone"] = phone

            databaseReference.push().setValue(phoneDirectory)
                .addOnSuccessListener {
                    // Aquí puedes limpiar los campos o mostrar un mensaje
                    binding.uploadName.text.clear()
                    binding.uploadOperator.text.clear()
                    binding.uploadLocation.text.clear()
                    binding.uploadPhone.text.clear()

                    Toast.makeText(this, "Saved",Toast.LENGTH_SHORT).show()

                    val intent = Intent(this@UploadActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Faild",Toast.LENGTH_SHORT).show()
                }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
