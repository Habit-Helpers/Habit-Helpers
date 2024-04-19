package com.example.habit_helper

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class UserGoalsAdapter(private var userGoals: List<Goal>, private val allowExpansion: Boolean) :
    RecyclerView.Adapter<UserGoalsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val goalNameTextView: TextView = itemView.findViewById(R.id.goalNameTextView)
        private val goalDescriptionTextView: TextView = itemView.findViewById(R.id.goalDescriptionTextView)
        private val goalStatusTextView: TextView = itemView.findViewById(R.id.goalStatusTextView)

        init {
            if (allowExpansion) {
                itemView.setOnClickListener {
                    val context = itemView.context
                    val recyclerView = itemView.parent as? RecyclerView
                    val parentViewGroup = recyclerView?.parent as? ViewGroup

                    parentViewGroup?.let { parent ->
                        val expandedView = LayoutInflater.from(context)
                            .inflate(R.layout.expanded_user_goals_layout, parent, false)

                        // Populate expanded view with all goals
                        val expandedRecyclerView: RecyclerView = expandedView.findViewById(R.id.expandedRecyclerView)
                        expandedRecyclerView.layoutManager = LinearLayoutManager(context)
                        expandedRecyclerView.adapter = UserGoalsAdapter(userGoals, false) // Disable expansion

                        // Adjust layout parameters to cover the whole screen
                        val layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        expandedView.layoutParams = layoutParams

                        // Add collapse button
                        val buttonCollapse = expandedView.findViewById<Button>(R.id.buttonCollapse)
                        buttonCollapse.setOnClickListener {
                            collapseRecyclerView(parent, recyclerView)
                        }

                        // Replace RecyclerView with expanded view
                        parent.removeView(recyclerView)
                        parent.addView(expandedView)
                    }
                }
            }
        }

        fun bind(goal: Goal) {
            goalNameTextView.text = goal.name
            goalDescriptionTextView.text = goal.description
            goalStatusTextView.text = goal.status
        }
    }

    private fun collapseRecyclerView(parent: ViewGroup, recyclerView: RecyclerView?) {
        recyclerView?.let {
            Log.d(TAG, "Removing expanded view from parent")
            val expandedView = parent.getChildAt(1) // Assuming expanded view is the second child
            Log.d(TAG, "Expanded view: $expandedView")
            if (expandedView != null) {
                parent.removeView(expandedView) // Remove the expanded view
                parent.addView(recyclerView) // Add back the RecyclerView
                Log.d(TAG, "Collapsed RecyclerView added back to parent")
            } else {
                Log.e(TAG, "Expanded view is null, cannot collapse RecyclerView")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d(TAG, "onCreateViewHolder called")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user_goal, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder called for position $position")
        holder.bind(userGoals[position])
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount called")
        return userGoals.size
    }

    fun updateGoals(newGoals: List<Goal>) {
        Log.d(TAG, "updateGoals called with ${newGoals.size} goals")
        userGoals = newGoals
        notifyDataSetChanged()
    }

    companion object {
        private const val TAG = "UserGoalsAdapter"
    }
}





