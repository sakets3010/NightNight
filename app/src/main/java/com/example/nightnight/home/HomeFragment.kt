package com.example.nightnight.home

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.TextViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nightnight.convertDurationToFormatted
import com.example.nightnight.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.coroutines.coroutineContext

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel by viewModels<HomeViewModel>()
    private var _binding: FragmentHomeBinding? = null
    private var _pauseOffset = 0L
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)




        binding.sleepRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)


        viewModel.tonight.observe(viewLifecycleOwner,{ night ->
            if (night != null) {
                binding.sleepWatch.base =SystemClock.elapsedRealtime().minus(System.currentTimeMillis().minus(night.initTime))
                binding.sleepWatch.start()
            }
        })

        binding.startSleepButton.setOnClickListener {
            binding.sleepWatch.base = SystemClock.elapsedRealtime().minus(_pauseOffset)
            binding.sleepWatch.start()
            viewModel.startTrack()
            Snackbar.make(requireView(), "sleep timer started", Snackbar.LENGTH_LONG).show()
        }
        binding.stopSleepButton.setOnClickListener {
            binding.sleepWatch.stop()
            _pauseOffset = (SystemClock.elapsedRealtime() - binding.sleepWatch.base)
            viewModel.stopTrack()
            Snackbar.make(requireView(), "sleep timer stopped", Snackbar.LENGTH_LONG).show()
        }

        viewModel.startButtonVisible.observe(viewLifecycleOwner, {
            binding.startSleepButton.isEnabled = it
        })
        viewModel.stopButtonVisible.observe(viewLifecycleOwner, {
            binding.stopSleepButton.isEnabled = it
        })

        viewModel.navigateToSleepQuality.observe(viewLifecycleOwner, { night ->
            night?.let {
                val action = HomeFragmentDirections.actionHomeFragmentToRatingFragment(
                    night.id,
                    _pauseOffset
                )
                findNavController().navigate(action)
                viewModel.doneNavigating()
                _pauseOffset = 0
            }
        })



        viewModel.nights.observe(viewLifecycleOwner, {
            it?.let {
                binding.sleepRecycler.adapter = NightAdapter(it)
            }
        })

        return binding.root
    }





    private fun setEmpty() {
        binding.sleepRecycler.visibility = View.GONE
        binding.emptyList.visibility = View.VISIBLE
        binding.emptyText.visibility = View.VISIBLE
    }

    private fun setNotEmpty() {
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