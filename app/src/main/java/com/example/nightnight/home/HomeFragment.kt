package com.example.nightnight.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nightnight.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel by viewModels<HomeViewModel>()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.sleepRecycler.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)

        binding.startSleepButton.setOnClickListener {
            viewModel.startTrack()
            onStartButtonPressed()
            Snackbar.make(requireView(),"sleep timer started",Snackbar.LENGTH_LONG).show()
        }
        binding.stopSleepButton.setOnClickListener {
            viewModel.stopTrack()
            onStopButtonPressed()
            Snackbar.make(requireView(),"sleep timer stopped",Snackbar.LENGTH_LONG).show()
        }

        viewModel.navigateToSleepQuality.observe(viewLifecycleOwner, { night ->
            night?.let {
                val action = HomeFragmentDirections.actionHomeFragmentToRatingFragment(night.id)
                findNavController().navigate(action)
                viewModel.doneNavigating()
            }
        })

        viewModel.nights.observe(viewLifecycleOwner, {
            it?.let {
                binding.sleepRecycler.adapter = NightAdapter(it)
            }
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
    private fun setEmpty(){
        binding.sleepRecycler.visibility = View.GONE
        binding.emptyList.visibility = View.VISIBLE
        binding.emptyText.visibility = View.VISIBLE
    }
    private fun setNotEmpty(){
        binding.sleepRecycler.visibility = View.VISIBLE
        binding.emptyList.visibility = View.GONE
        binding.emptyText.visibility = View.GONE
    }
    private fun setLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.emptyList.visibility = View.GONE
        binding.emptyText.visibility = View.GONE
    }

    private fun setNotLoading() {
        binding.progressBar.visibility = View.GONE
        binding.emptyText.visibility = View.VISIBLE
        binding.emptyList.visibility = View.VISIBLE
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}