package infosys.com.kotlinmvvmsample.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

import android.view.LayoutInflater
import android.databinding.DataBindingUtil
import infosys.com.kotlinmvvmsample.service.model.Fact
import infosys.com.kotlinmvvmsample.databinding.FactsListItemBinding


class FactAdapter(private val FactList: MutableList<Fact>): RecyclerView.Adapter<FactAdapter.FactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FactViewHolder {
        val binding = DataBindingUtil.inflate<FactsListItemBinding>(LayoutInflater.from(parent.context), R.layout.facts_list_item,
                        parent, false)

        return FactViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return FactList.size
    }

    override fun onBindViewHolder(holder: FactViewHolder, position: Int) {
        holder.binding.project=FactList.get(position)
        holder.binding.executePendingBindings()
    }

    class FactViewHolder(val binding: FactsListItemBinding) : RecyclerView.ViewHolder(binding.root)
}