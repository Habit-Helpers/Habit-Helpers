package com.example.habit_helper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecommendedChangesAdapter(private val recommendedChanges: List<String>) : RecyclerView.Adapter<RecommendedChangesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recommended_change, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(recommendedChanges[position])
    }

    override fun getItemCount(): Int {
        return recommendedChanges.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val recommendedChangeTextView: TextView = itemView.findViewById(R.id.textViewRecommendation)

        fun bind(recommendedChange: String) {
            recommendedChangeTextView.text = recommendedChange
        }
    }
}
