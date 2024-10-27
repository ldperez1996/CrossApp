package com.example.crossplan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crossplan.adapters.ExerciseAdapter
import com.example.crossplan.models.Exercise
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import android.view.View


class ExercisesActivity : AppCompatActivity() {
    private lateinit var exerciseRecyclerView: RecyclerView
    private lateinit var exerciseAdapter: ExerciseAdapter
    private var exerciseList: MutableList<Exercise> = mutableListOf()
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercises)

        exerciseRecyclerView = findViewById(R.id.exerciseRecyclerView)
        exerciseAdapter = ExerciseAdapter(exerciseList)
        exerciseRecyclerView.layoutManager = LinearLayoutManager(this)
        exerciseRecyclerView.adapter = exerciseAdapter
        database = FirebaseDatabase.getInstance().reference.child("exercises")

        // Cargar los datos desde Firebase
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                exerciseList.clear()
                for (exerciseSnapshot in snapshot.children) {
                    val exercise = exerciseSnapshot.getValue(Exercise::class.java)
                    if (exercise != null) {
                        exerciseList.add(exercise)
                    }
                }
                exerciseAdapter.notifyDataSetChanged()
                showSnackbar("Datos de ejercicios cargados")
            }

            override fun onCancelled(error: DatabaseError) {
                showSnackbar("Error al cargar datos: ${error.message}")
            }
        })
    }

    private fun showSnackbar(message: String) {
        val rootView = findViewById<View>(android.R.id.content)
        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show()
    }
}
