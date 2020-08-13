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
import com.google.android.material.snackbar.Snackbar

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
            update(1,args.docId)
        }
        binding.upset.setOnClickListener {
            update(2,args.docId)
        }
        binding.satisfactory.setOnClickListener {
            update(3,args.docId)
        }
        binding.happy.setOnClickListener {
            update(4,args.docId)
        }
        binding.veryHappy.setOnClickListener {
            update(5,args.docId)
        }
        return binding.root
    }

  private fun update(rating:Int,docId:String){
      viewModel.updateRating(rating,docId)
      findNavController().navigate(R.id.action_ratingFragment_to_homeFragment)
      Snackbar.make(requireView(),"sleep record added", Snackbar.LENGTH_LONG).show()
  }

}