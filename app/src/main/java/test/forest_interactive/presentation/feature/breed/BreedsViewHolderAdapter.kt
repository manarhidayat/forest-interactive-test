package test.forest_interactive.presentation.feature.breed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import test.forest_interactive.R
import test.forest_interactive.databinding.ItemBreedBinding
import test.forest_interactive.data.model.breed.BreedsItem

class BreedsAdapter(
    private val data: ArrayList<BreedsItem>
) : RecyclerView.Adapter<BreedViewHolder>() {


    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedViewHolder {
        val itemBreedBinding: ItemBreedBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_breed,
            parent,
            false
        )
        return BreedViewHolder(
            itemBreedBinding
        )
    }

    override fun onBindViewHolder(holder: BreedViewHolder, position: Int) {
        holder.itemBreedBinding.breed = data[position]
    }

    fun setUpData(breeds: List<BreedsItem>) {
        data.clear()
        data.addAll(breeds)
        notifyDataSetChanged()
    }
}


class BreedViewHolder(
    val itemBreedBinding: ItemBreedBinding
) : RecyclerView.ViewHolder(itemBreedBinding.root)