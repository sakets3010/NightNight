package com.example.nightnight.monitor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.nightnight.R
import com.example.nightnight.databinding.FragmentGraphBinding
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import dagger.hilt.android.AndroidEntryPoint
import org.eazegraph.lib.models.BarModel

@AndroidEntryPoint
class GraphFragment : Fragment() {

    private val viewModel by viewModels<GraphViewmodel>()
    private var _binding: FragmentGraphBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentGraphBinding.inflate(inflater, container, false)

        setBarChart()

        return binding.root
    }

    private fun setBarChart() {
        viewModel.nights.observe(viewLifecycleOwner, {
            it.reversed().forEach { night ->
                binding.barChart.addBar(
                    BarModel(
                        viewModel.getDateTime(night.initTime),
                        night.sleepRating.toFloat(), R.color.colorBlack
                    )
                )
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}