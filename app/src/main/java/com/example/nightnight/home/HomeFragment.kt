package com.example.nightnight.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.nightnight.R
import com.example.nightnight.databinding.FragmentHomeBinding
import com.example.nightnight.helper.SleepAdapter
import com.example.nightnight.home.HomeViewModel.Util.Companion._navigationDone
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )
        viewModel = ViewModelProvider(this)
            .get(HomeViewModel::class.java)

        binding.startSleepButton.setOnClickListener {
            viewModel.onStarted()
            onStartButtonPressed()
        }
        binding.stopSleepButton.setOnClickListener {
            viewModel.onStopped()
            val action =
                HomeFragmentDirections.actionHomeFragmentToRatingFragment(viewModel.docId)
            findNavController().navigate(action)
            onStopButtonPressed()
        }
        binding.clearSleepButton.setOnClickListener {
            MaterialAlertDialogBuilder(
                requireContext()
            )
                .setTitle("Delete Sleep Data?")
                .setMessage("Data will be gone forever")
                .setNeutralButton("Cancel") { _, _ ->
                    // Do nothing
                }
                .setPositiveButton("Delete") { _, _ ->
                    viewModel.onClear()
                }
                .show()

        }
        _navigationDone.observe(viewLifecycleOwner, Observer{
            if(it){
                viewModel.onDone().observe(viewLifecycleOwner, Observer {sleepRecord ->
                    Log.d("listing", "data listing: $sleepRecord")
                    binding.sleepRecycler.adapter = SleepAdapter(sleepRecord)
                })
            }
            _navigationDone.value=false
        })
        return binding.root
    }


    private fun onStartButtonPressed(){
        binding.startSleepButton.visibility = View.GONE
        binding.stopSleepButton.visibility = View.VISIBLE
    }
    private fun onStopButtonPressed(){
        binding.startSleepButton.visibility = View.VISIBLE
        binding.stopSleepButton.visibility = View.GONE
    }

}