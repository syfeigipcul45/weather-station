package com.example.weatherstation.form.ui.pompa

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherstation.R
import com.example.weatherstation.databinding.FragmentPompaBinding
import com.example.weatherstation.databinding.FragmentSensorBinding
import com.example.weatherstation.model.Pompa
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class PompaFragment : Fragment() {

    private var _binding: FragmentPompaBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: PompaViewModel
    var dataPump: String? = null

    companion object {
        val TAG = "PompaFragment"
        fun newInstance() = PompaFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPompaBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(PompaViewModel::class.java)
        viewModel.statusRelay.observe(viewLifecycleOwner) { status ->
            status?.let {
                dataPump = it
                getImgPompa(dataPump.toString())
                buttonPump(dataPump.toString())
                Toast.makeText(requireContext(), "Status: $dataPump", Toast.LENGTH_SHORT).show()
            } ?: run {
                Log.e("PompaFragment", "Status is null")
            }
        }


    }

    private fun buttonPump(data: String) {
        if (data.equals("1")) {
            binding.btnPompa.setOnClickListener {
                val statusRelay = "0"
                viewModel.updateStatusRelay(statusRelay)
            }
            binding.txtStatus.text = "Pompa: Nyala"
        } else {
            binding.btnPompa.setOnClickListener {
                val statusRelay = "1"
                viewModel.updateStatusRelay(statusRelay)
            }
            binding.txtStatus.text = "Pompa: Mati"
        }
    }

    private fun getImgPompa(data: String) {
        if (data.equals("1")) {
            binding.btnPompa.setImageResource(R.drawable.btn_on_xs)
        } else {
            binding.btnPompa.setImageResource(R.drawable.btn_off_xs)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}