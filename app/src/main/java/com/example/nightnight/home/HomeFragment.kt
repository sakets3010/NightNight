package com.example.nightnight.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nightnight.LoginActivity
import com.example.nightnight.R
import com.example.nightnight.databinding.FragmentHomeBinding
import com.example.nightnight.helper.SleepAdapter
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso


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

        Picasso.get().load(GoogleSignIn.getLastSignedInAccount(requireContext())?.photoUrl).into(binding.profilePic)

        binding.startSleepButton.setOnClickListener {
            viewModel.onStarted()
            onStartButtonPressed()
            Snackbar.make(requireView(),"sleep timer started",Snackbar.LENGTH_LONG).show()
        }
        binding.stopSleepButton.setOnClickListener {
            viewModel.onStopped()
            val action =
                HomeFragmentDirections.actionHomeFragmentToRatingFragment(viewModel.docId)
            findNavController().navigate(action)
            Snackbar.make(requireView(),"sleep timer stopped",Snackbar.LENGTH_LONG).show()
            onStopButtonPressed()
        }
        binding.profilePic.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Sign Out?")
                .setMessage("You will be signed out of the app")
                .setNeutralButton("cancel") { dialog, which ->
                    // Do nothing
                }
                .setPositiveButton("sign out") { dialog, which ->
                    viewModel.signUserOut()
                    startActivity(Intent(requireContext(), LoginActivity::class.java))
                }
                .show()

        }
        setLoading()
        viewModel.onDone().observe(viewLifecycleOwner, Observer {sleepRecord ->
         if(sleepRecord!=null) {
             setNotLoading()
             if (sleepRecord.isEmpty()) {
                 setEmpty()
             } else {
                 setNotEmpty()
                 Log.d("listing", "data listing: $sleepRecord")
                 binding.sleepRecycler.adapter = SleepAdapter(sleepRecord)
                 binding.sleepRecycler.layoutManager =
                     LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, true)
             }
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

}