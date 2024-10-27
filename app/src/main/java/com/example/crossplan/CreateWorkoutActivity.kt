package com.example.crossplan

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.example.crossplan.models.Workout
import android.view.View


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
                            showSnackbar("Entreno creado exitosamente")
                            Log.d("CreateWorkoutActivity", "Workout saved successfully")
                            finish()
                        } else {
                            showSnackbar("Error al crear entreno: ${task.exception?.message}")
                            Log.e("CreateWorkoutActivity", "Error: ${task.exception?.message}")
                        }
                    }.addOnFailureListener { exception ->
                        showSnackbar("Error al crear entreno: ${exception.message}")
                        Log.e("CreateWorkoutActivity", "Failure: ${exception.message}")
                    }
                } else {
                    showSnackbar("Error al generar ID de entreno")
                    Log.e("CreateWorkoutActivity", "Error al generar ID de entreno")
                }
            } else {
                showSnackbar("Por favor completa todos los campos")
                Log.w("CreateWorkoutActivity", "Campos vacíos")
            }
        }
    }

    private fun showSnackbar(message: String) {
        val rootView = findViewById<View>(android.R.id.content)
        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show()
    }
}
