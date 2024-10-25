package com.example.crossplan

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crossplan.adapters.WorkoutAdapter
import com.example.crossplan.fragments.ExercisesFragment
import com.example.crossplan.fragments.HistoryFragment
import com.example.crossplan.fragments.HomeFragment
import com.example.crossplan.models.Workout
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var createWorkoutButton: Button
    private lateinit var workoutRecyclerView: RecyclerView
    private lateinit var workoutAdapter: WorkoutAdapter
    private var workoutList: MutableList<Workout> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createWorkoutButton = findViewById(R.id.createWorkoutButton)
        workoutRecyclerView = findViewById(R.id.workoutRecyclerView)
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)

        createWorkoutButton.setOnClickListener {
            val intent = Intent(this, CreateWorkoutActivity::class.java)
            startActivity(intent)
        }

        workoutAdapter = WorkoutAdapter(workoutList)
        workoutRecyclerView.layoutManager = LinearLayoutManager(this)
        workoutRecyclerView.adapter = workoutAdapter

        // Manejar la navegación inferior
        bottomNavigationView.setOnItemSelectedListener { item ->
            var selectedFragment: Fragment? = null
            when (item.itemId) {
                R.id.nav_home -> selectedFragment = HomeFragment()
                R.id.nav_history -> selectedFragment = HistoryFragment()
                R.id.nav_exercises -> selectedFragment = ExercisesFragment()
            }
            selectedFragment?.let {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, it).commit()
            }
            true
        }

        // Establecer el fragmento inicial
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment()).commit()

        // Aquí deberías cargar los entrenos desde la base de datos y actualizar workoutList y workoutAdapter
    }
}
