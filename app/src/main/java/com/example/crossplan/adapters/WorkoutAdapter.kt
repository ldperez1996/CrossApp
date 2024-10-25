package com.example.crossplan.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.crossplan.R
import com.example.crossplan.models.Workout

class WorkoutAdapter(private val workoutList: List<Workout>) :
    RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_workout, parent, false)
        return WorkoutViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val currentWorkout = workoutList[position]
        holder.workoutName.text = currentWorkout.name
        holder.workoutDescription.text = currentWorkout.description
    }

    override fun getItemCount(): Int {
        return workoutList.size
    }

    class WorkoutViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val workoutName: TextView = itemView.findViewById(R.id.workoutName)
        val workoutDescription: TextView = itemView.findViewById(R.id.workoutDescription)
    }
}
