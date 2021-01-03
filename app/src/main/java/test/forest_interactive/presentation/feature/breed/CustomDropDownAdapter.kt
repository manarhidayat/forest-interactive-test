package test.forest_interactive.presentation.feature.breed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import test.forest_interactive.R
import test.forest_interactive.data.entities.BreedEntity
import test.forest_interactive.databinding.ItemBreedBinding
import test.forest_interactive.data.model.breed.BreedsItem


class CustomDropDownAdapter() : BaseAdapter() {
    private val dataSource = ArrayList<BreedsItem>()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {

        var holder: ItemHolder
        val itemBreedBinding: ItemBreedBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent?.context),
            R.layout.item_breed,
            parent,
            false
        )

        holder =
            ItemHolder(
                itemBreedBinding
            )
        holder.view = itemBreedBinding.root
        holder.view!!.tag = holder

        holder.binding!!.breed = dataSource[position]

        return holder.view
    }

    override fun getItem(position: Int): Any? {
        return dataSource[position];
    }

    override fun getCount(): Int {
        return dataSource.size;
    }

    override fun getItemId(position: Int): Long {
        return position.toLong();
    }

    fun setUpData(breeds: List<BreedsItem>) {
        dataSource.clear()
        dataSource.addAll(breeds)
        notifyDataSetChanged()
    }

    private class ItemHolder(itemBinding: ItemBreedBinding) {
        var view: View? = null
        var binding: ItemBreedBinding? = null

        init {
            this.view = itemBinding.root;
            this.binding = itemBinding;
        }
    }

}