package com.example.phonecontact

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.example.phonecontact.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var contactList: MutableList<Person>
    private val adapter = PersonAdapter()

    @SuppressLint("Range", "NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        contactList = mutableListOf()

        binding.recyclerView.adapter = adapter
        adapter.models = contactList

        val contact = context?.contentResolver?.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        if (contact != null) {
            while (contact.moveToNext()) {
                val name =
                    contact.getString(contact.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val phoneNumber =
                    contact.getString(contact.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                val img =
                    contact.getString(contact.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI))

                Log.d("TTTT", "Photo: $img")

                val obj = Person(name, phoneNumber, null)
                if (img != null) {
                    val bitmapImage = MediaStore.Images.Media.getBitmap(context?.contentResolver, Uri.parse(img))
                    obj.img = bitmapImage
                }
                contactList.add(obj)

            }
        }
        contact?.close()

        adapter.setOnUserClickListener {
            (requireActivity() as MainActivity).setFragment(newInstance(it.name, it.phoneNumber, it.img), true)
        }

        binding.recyclerView.adapter?.notifyDataSetChanged()
    }

    companion object {
        fun newInstance(name: String, phoneNumber: String, image: Bitmap?): ProfileFragment {
            val fragment = ProfileFragment()
            val bundle = Bundle()
            bundle.putString("name", name)
            bundle.putString("phoneNumber", phoneNumber)
            bundle.putParcelable("image", image)
            fragment.arguments = bundle
            return fragment
        }
    }
}