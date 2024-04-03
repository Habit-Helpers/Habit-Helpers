package com.example.habit_helper


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find the summary cards views by their IDs
        val stepsCard = view.findViewById<LinearLayout>(R.id.linearLayout2)
        val sleepCard = view.findViewById<LinearLayout>(R.id.linearLayout3)
        val waterCard = view.findViewById<LinearLayout>(R.id.linearLayout4)
        val caloriesCard = view.findViewById<LinearLayout>(R.id.linearLayout5)

        // Set onClickListener for each summary card
        stepsCard.setOnClickListener {
            navigateToActivityFragment()
        }

        sleepCard.setOnClickListener {
            navigateToActivityFragment()
        }

        waterCard.setOnClickListener {
            navigateToActivityFragment()
        }

        caloriesCard.setOnClickListener {
            navigateToActivityFragment()
        }
    }

    // Function to navigate to the activity fragment
    private fun navigateToActivityFragment() {
        // Use the Navigation component to navigate to the activity fragment
        findNavController().navigate(R.id.action_homeFragment_to_activityFragment)

        findNavController().navigate(R.id.activitiesFragment)
    }

}
