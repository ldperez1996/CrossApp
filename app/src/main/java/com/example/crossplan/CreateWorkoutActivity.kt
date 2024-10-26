package com.example.crossplan

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.example.crossplan.models.Workout

class CreateWorkoutActivity : AppCompatActivity() {
    private lateinit var workoutNameEditText: EditText
    private lateinit var workoutDescriptionEditText: EditText
    private lateinit var createWorkoutButton: Button
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_workout)

        workoutNameEditText = findViewById(R.id.workoutNameEditText)
        workoutDescriptionEditText = findViewById(R.id.workoutDescriptionEditText)
        createWorkoutButton = findViewById(R.id.createWorkoutButton)
        database = FirebaseDatabase.getInstance().reference.child("workouts")

        createWorkoutButton.setOnClickListener {
            val workoutName = workoutNameEditText.text.toString()
            val workoutDescription = workoutDescriptionEditText.text.toString()

            Log.d("CreateWorkoutActivity", "Button clicked")
            if (workoutName.isNotEmpty() && workoutDescription.isNotEmpty()) {
                val workoutId = database.push().key
                if (workoutId != null) {
                    val workout = Workout(workoutId, workoutName, workoutDescription)
                    Log.d("CreateWorkoutActivity", "Workout object created: $workout")
                    database.child(workoutId).setValue(workout).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Entreno creado exitosamente", Toast.LENGTH_SHORT).show()
                            Log.d("CreateWorkoutActivity", "Workout saved successfully")
                            finish()
                        } else {
                            Toast.makeText(this, "Error al crear entreno: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                            Log.e("CreateWorkoutActivity", "Error: ${task.exception?.message}")
                        }
                    }.addOnFailureListener { exception ->
                        Toast.makeText(this, "Error al crear entreno: ${exception.message}", Toast.LENGTH_SHORT).show()
                        Log.e("CreateWorkoutActivity", "Failure: ${exception.message}")
                    }
                } else {
                    Toast.makeText(this, "Error al generar ID de entreno", Toast.LENGTH_SHORT).show()
                    Log.e("CreateWorkoutActivity", "Error al generar ID de entreno")
                }
            } else {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
                Log.w("CreateWorkoutActivity", "Campos vacíos")
            }
        }
    }
}
