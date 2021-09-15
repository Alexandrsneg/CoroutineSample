package com.example.coroutinesample.view

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coroutinesample.repository.University

class UniversitiesAdapter(private val universitiesList: ArrayList<University>) :
    RecyclerView.Adapter<UniversitiesAdapter.ViewHolder>() {

    class ViewHolder(private val item: UniversityItemView)
        : RecyclerView.ViewHolder(item){
            fun bind(university: University) {
                item.bind(university.name ?: "unknown")
            }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UniversitiesAdapter.ViewHolder {
//        val binding = UniversityItemViewBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(UniversityItemView(parent.context))
    }

    override fun onBindViewHolder(holder: UniversitiesAdapter.ViewHolder, position: Int) {
        val university: University = universitiesList[position]
        holder.bind(university)
    }

    override fun getItemCount() = universitiesList.size

    fun updateDataSet(university: University) {
        universitiesList.add(university)
        notifyItemInserted(itemCount)
    }
}