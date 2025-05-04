package com.johncodeos.scrolltotopexample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gototopbutton.R
import com.example.gototopbutton.model.Car

class Cars_RVAdapter(private var carsCells: Car) : RecyclerView.Adapter<Cars_RVAdapter.CarsViewHolder>() {

    class CarsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val carMake: TextView = itemView.findViewById(R.id.car_make)
        val carModel: TextView = itemView.findViewById(R.id.car_model)
        val carModelYear: TextView = itemView.findViewById(R.id.car_model_year)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.car_row, parent, false)
        return CarsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return carsCells.size
    }

    override fun onBindViewHolder(holder: CarsViewHolder, position: Int) {
        val currentCar = carsCells[position]
        holder.carMake.text = currentCar.carMake
        holder.carModel.text = currentCar.carModel
        holder.carModelYear.text = currentCar.carModelYear.toString()
    }
}