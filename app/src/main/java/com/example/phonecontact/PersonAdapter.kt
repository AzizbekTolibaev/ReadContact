package com.example.phonecontact

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColor
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.phonecontact.databinding.ItemPersonBinding
import kotlin.random.Random

class PersonAdapter(): RecyclerView.Adapter<PersonAdapter.PersonViewHolder>() {

    private var onClickUserListener: ((Person) -> Unit)? = null

    fun setOnUserClickListener(block: (Person) -> Unit) {
        onClickUserListener = block
    }

    inner class PersonViewHolder(private val binding: ItemPersonBinding): ViewHolder(binding.root) {
        fun bind() {
            val position = models[adapterPosition]
            binding.name.text = position.name
            if (position.img != null) {
                binding.imgPerson.setImageBitmap(position.img)
            } else {
                val colorList = listOf("#AF5BF5", "#FE62B7", "#4DCDE5", "#FA8F3D", "#FBC834", "#5BB972")
                val randomNumber = Random.nextInt(0, 6)
                binding.linear.setBackgroundColor(Color.parseColor(colorList[randomNumber]))
                binding.imgPerson.setImageResource(R.drawable.ic_round_person_24)
            }
            binding.root.setOnClickListener {
                onClickUserListener?.invoke(position)
            }
        }
    }

    var models = mutableListOf<Person>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return models.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        return PersonViewHolder(
            ItemPersonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bind()
    }
}
