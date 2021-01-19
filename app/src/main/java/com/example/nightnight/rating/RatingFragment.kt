package com.example.nightnight.rating

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.nightnight.R
import com.example.nightnight.databinding.RatingFragmentBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RatingFragment : Fragment() {

    private val viewModel by viewModels<RatingViewModel>()
    private var _binding: RatingFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val args: RatingFragmentArgs by navArgs()
        _binding = RatingFragmentBinding.inflate(inflater, container, false)

        binding.veryUpset.setOnClickListener {
            update(1,args.id)
        }
        binding.upset.setOnClickListener {
            update(2,args.id)
        }
        binding.satisfactory.setOnClickListener {
            update(3,args.id)
        }
        binding.happy.setOnClickListener {
            update(4,args.id)
        }
        binding.veryHappy.setOnClickListener {
            update(5,args.id)
        }
        return binding.root
    }

  private fun update(rating:Int,key:Long){
      viewModel.updateRating(rating,key)
      findNavController().navigate(R.id.action_ratingFragment_to_homeFragment)
      Snackbar.make(requireView(),"sleep record added", Snackbar.LENGTH_LONG).show()
  }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}