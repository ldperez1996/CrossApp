package com.example.crossplan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crossplan.adapters.HistoryAdapter
import com.example.crossplan.models.Workout
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import android.view.View


class HistoryActivity : AppCompatActivity() {
    private lateinit var historyRecyclerView: RecyclerView
    private lateinit var historyAdapter: HistoryAdapter
    private var workoutHistoryList: MutableList<Workout> = mutableListOf()
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        historyRecyclerView = findViewById(R.id.historyRecyclerView)
        historyAdapter = HistoryAdapter(workoutHistoryList)
        historyRecyclerView.layoutManager = LinearLayoutManager(this)
        historyRecyclerView.adapter = historyAdapter
        database = FirebaseDatabase.getInstance().reference.child("workouts")

        // Cargar los datos desde Firebase
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                workoutHistoryList.clear()
                for (workoutSnapshot in snapshot.children) {
                    val workout = workoutSnapshot.getValue(Workout::class.java)
                    if (workout != null) {
                        workoutHistoryList.add(workout)
                    }
                }
                historyAdapter.notifyDataSetChanged()
                showSnackbar("Datos de historial cargados")
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
