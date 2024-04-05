package com.example.habit_helper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserGoalsAdapter(private val userGoals: List<String>) : RecyclerView.Adapter<UserGoalsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user_goal, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(userGoals[position])
    }

    override fun getItemCount(): Int {
        return userGoals.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userGoalTextView: TextView = itemView.findViewById(R.id.userGoalTextView)

        fun bind(userGoal: String) {
            userGoalTextView.text = userGoal
        }
    }
}
