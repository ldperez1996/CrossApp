package com.example.crossplan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crossplan.adapters.ExerciseAdapter
import com.example.crossplan.models.Exercise
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

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
            }

            override fun onCancelled(error: DatabaseError) {
                // Manejar el error
            }
        })
    }
}
