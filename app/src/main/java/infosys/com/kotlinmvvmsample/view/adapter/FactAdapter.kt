package infosys.com.kotlinmvvmsample.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

import android.view.LayoutInflater
import android.databinding.DataBindingUtil

import android.view.View
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import infosys.com.kotlinmvvmsample.R
import infosys.com.kotlinmvvmsample.service.model.Fact
import infosys.com.kotlinmvvmsample.databinding.FactsListItemBinding


class FactAdapter(private val FactList: MutableList<Fact>) : RecyclerView.Adapter<FactAdapter.FactViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FactViewHolder {
        val binding = DataBindingUtil.inflate<FactsListItemBinding>(LayoutInflater.from(parent.context), R.layout.facts_list_item,
                parent, false)
        binding.isImageLoading = true
        return FactViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return FactList.size
    }

    override fun onBindViewHolder(holder: FactViewHolder, position: Int) {
        holder.binding.fact = FactList.get(position)
        val imageUrl = FactList.get(position).imageHref
        if (!imageUrl.isNullOrEmpty()) {
            //loadingProgressBar.setVisibility(View.VISIBLE)

            // initiate picasso to load image
            Picasso.get()
                    .load(imageUrl)
                    .into(holder.binding.imvFactImage, object : Callback {
                        override fun onSuccess() {
                            //loadingProgressBar.setVisibility(View.GONE)
                        }

                        override fun onError(e: Exception) {
                            //loadingProgressBar.setVisibility(View.GONE)
                            holder.binding.imvFactImage.setVisibility(View.GONE)
                        }

                    })
        } else {
            // image url is null, hide image view
            holder.binding.imvFactImage.setVisibility(View.GONE)
        }

        if (imageUrl.isNullOrEmpty() && (FactList.get(position).title).isNullOrEmpty() && (FactList.get(position).description).isNullOrEmpty()) {
            holder.binding.root.visibility = View.GONE
        } else {
            holder.binding.root.visibility = View.VISIBLE
        }

        holder.binding.executePendingBindings()
    }

    class FactViewHolder(val binding: FactsListItemBinding) : RecyclerView.ViewHolder(binding.root)

}