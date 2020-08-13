package com.example.nightnight.rating

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.nightnight.R
import com.example.nightnight.databinding.RatingFragmentBinding

class RatingFragment : Fragment() {

    private lateinit var viewModel: RatingViewModel
    private lateinit var binding:RatingFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args: RatingFragmentArgs by navArgs()
        binding = DataBindingUtil.inflate(inflater,R.layout.rating_fragment,container,false)
        viewModel = ViewModelProvider(this).get(RatingViewModel::class.java)

        binding.veryUpset.setOnClickListener {
            viewModel.updateRating(1,args.docId)
            findNavController().navigate(R.id.action_ratingFragment_to_homeFragment)
        }
        binding.upset.setOnClickListener {
            viewModel.updateRating(2,args.docId)
            findNavController().navigate(R.id.action_ratingFragment_to_homeFragment)
        }
        binding.satisfactory.setOnClickListener {
            viewModel.updateRating(3,args.docId)
            findNavController().navigate(R.id.action_ratingFragment_to_homeFragment)
        }
        binding.happy.setOnClickListener {
            viewModel.updateRating(4,args.docId)
            findNavController().navigate(R.id.action_ratingFragment_to_homeFragment)
        }
        binding.veryHappy.setOnClickListener {
            viewModel.updateRating(5,args.docId)
            findNavController().navigate(R.id.action_ratingFragment_to_homeFragment)
        }
        return binding.root
    }



}