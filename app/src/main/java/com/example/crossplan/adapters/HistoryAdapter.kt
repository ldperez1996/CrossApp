package com.example.crossplan.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.crossplan.R
import com.example.crossplan.models.Workout

class HistoryAdapter(private val workoutHistoryList: List<Workout>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return HistoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val currentWorkout = workoutHistoryList[position]
        holder.workoutName.text = currentWorkout.name
        holder.workoutDescription.text = currentWorkout.description
    }

    override fun getItemCount(): Int {
        return workoutHistoryList.size
    }

    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val workoutName: TextView = itemView.findViewById(R.id.workoutName)
        val workoutDescription: TextView = itemView.findViewById(R.id.workoutDescription)
    }
}
