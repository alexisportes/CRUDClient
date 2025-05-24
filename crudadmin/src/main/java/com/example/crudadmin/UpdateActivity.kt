package com.example.crudadmin

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.crudadmin.databinding.ActivityUpdateBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Mantener el edge to edge y padding de sistema
        enableEdgeToEdge()
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Lógica de actualización con Firebase
        binding.updateButton.setOnClickListener {
            val referencePhone = binding.referencePhone.text.toString()
            val updateName = binding.updateName.text.toString()
            val updateOperator = binding.updateOperator.text.toString()
            val updateLocation = binding.updateLocation.text.toString()
            updateData(referencePhone, updateName, updateOperator, updateLocation)
        }
    }

    private fun updateData(phone: String, name: String, operator: String, location: String) {
        database = FirebaseDatabase.getInstance().getReference("Users")
        val user = mapOf<String, String>(
            "name" to name,
            "operator" to operator,
            "location" to location
        )
        database.child(phone).updateChildren(user).addOnSuccessListener {
            binding.referencePhone.text.clear()
            binding.updateName.text.clear()
            binding.updateOperator.text.clear()
            binding.updateLocation.text.clear()
            Toast.makeText(this, "Successfully Updated", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to Update", Toast.LENGTH_SHORT).show()
        }
    }
}
