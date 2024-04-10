package com.example.habit_helper

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserGoalsAdapter(private var userGoals: List<Goal>) : RecyclerView.Adapter<UserGoalsAdapter.ViewHolder>() {

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

    @SuppressLint("NotifyDataSetChanged")
    fun updateGoals(newGoals: List<Goal>) {
        userGoals = newGoals
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val goalNameTextView: TextView = itemView.findViewById(R.id.goalNameTextView)
        private val goalDescriptionTextView: TextView = itemView.findViewById(R.id.goalDescriptionTextView)
        private val goalStatusTextView: TextView = itemView.findViewById(R.id.goalStatusTextView)

        fun bind(goal: Goal) {
            goalNameTextView.text = goal.name
            goalDescriptionTextView.text = goal.description
            goalStatusTextView.text = goal.status
        }
    }
}

