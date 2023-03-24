package com.example.phonecontact

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.example.phonecontact.databinding.FragmentProfileBinding

class ProfileFragment: Fragment(R.layout.fragment_profile) {
    private lateinit var binding: FragmentProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        val name = arguments?.getString("name").toString()
        val phoneNumber = arguments?.getString("phoneNumber").toString()

        binding.tvName.text = name
        binding.tvPhoneNumber.text = phoneNumber

        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        val bundle = arguments
        val bitmap = bundle?.getParcelable<Bitmap>("image")

        if (bitmap != null) {
            binding.imgPerson.setImageBitmap(bitmap)
        } else {
            binding.imgPerson.setImageResource(R.drawable.ic_round_person_24)
        }

        binding.btnCall.setOnClickListener {
            Log.d("FFFF", "phoneNumber: $phoneNumber")
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:$phoneNumber")
            startActivity(dialIntent)
        }
    }
}