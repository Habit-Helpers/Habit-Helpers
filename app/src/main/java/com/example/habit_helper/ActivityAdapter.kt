package com.example.habit_helper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ActivityAdapter(private var activities: List<ActivityItem>) :
    RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder>() {

    inner class ActivityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.activityNameTextView)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.activityDescriptionTextView)
        private val colorLabelTextView: TextView = itemView.findViewById(R.id.colorLabelTextView)

        fun bind(activity: ActivityItem) {
            nameTextView.text = activity.name
            descriptionTextView.text = activity.description
            colorLabelTextView.text = activity.colorLabel
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_activity, parent, false)
        return ActivityViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        val activity = activities[position]
        holder.bind(activity)
    }

    override fun getItemCount(): Int {
        return activities.size
    }

    fun updateActivities(newActivities: List<ActivityItem>) {
        activities = newActivities
        notifyDataSetChanged()
    }
}
