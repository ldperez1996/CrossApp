package com.example.crossplan

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crossplan.adapters.WorkoutAdapter
import com.example.crossplan.fragments.ExercisesFragment
import com.example.crossplan.fragments.HistoryFragment
import com.example.crossplan.fragments.HomeFragment
import com.example.crossplan.models.Workout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var createWorkoutButton: Button
    private lateinit var workoutRecyclerView: RecyclerView
    private lateinit var workoutAdapter: WorkoutAdapter
    private var workoutList: MutableList<Workout> = mutableListOf()
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        if (auth.currentUser == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

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
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment()).commit()
        }

        // Cargar datos en un hilo separado
        loadData()
    }

    private fun loadData() {
        lifecycleScope.launch(Dispatchers.IO) {
            // Cargar datos aquí (simulación de carga de datos)
            val fetchedWorkouts: MutableList<Workout> = fetchWorkoutsFromDatabase()

            // Actualizar UI en el hilo principal
            withContext(Dispatchers.Main) {
                workoutList.addAll(fetchedWorkouts)
                workoutAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun fetchWorkoutsFromDatabase(): MutableList<Workout> {
        // Simulación de la carga de datos desde la base de datos
        return mutableListOf(
            Workout("1", "Workout 1", "Description 1"),
            Workout("2", "Workout 2", "Description 2")
        )
    }
}
