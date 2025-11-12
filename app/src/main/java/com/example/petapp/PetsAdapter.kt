package com.example.petapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PetsAdapter(private val pets: List<PetInfo>) : RecyclerView.Adapter<PetsAdapter.PetViewHolder>() {

    class PetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val petName: TextView = itemView.findViewById(R.id.petNameText)
        val petSpecies: TextView = itemView.findViewById(R.id.petSpeciesText)
        val petBreed: TextView = itemView.findViewById(R.id.petBreedText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pet_item, parent, false)
        return PetViewHolder(view)
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        val pet = pets[position]
        holder.petName.text = pet.name
        holder.petSpecies.text = pet.species
        holder.petBreed.text = pet.breed
    }

    override fun getItemCount(): Int = pets.size
}
