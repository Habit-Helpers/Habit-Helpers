package com.example.habit_helper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ActivityAdapter(private val activityList: List<ActivityItem>) : RecyclerView.Adapter<ActivityAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val activityNameTextView: TextView = itemView.findViewById(R.id.activityNameTextView)
        val activityDescriptionTextView: TextView = itemView.findViewById(R.id.activityDescriptionTextView)
        val colorLabelTextView: TextView = itemView.findViewById(R.id.colorLabelTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_activity, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val activity = activityList[position]
        holder.activityNameTextView.text = activity.name
        holder.activityDescriptionTextView.text = activity.description
        holder.colorLabelTextView.text = activity.colorLabel
    }

    override fun getItemCount(): Int {
        return activityList.size
    }
}
