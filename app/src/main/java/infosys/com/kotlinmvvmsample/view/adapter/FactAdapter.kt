package infosys.com.kotlinmvvmsample.view.adapter

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

import android.view.LayoutInflater
import android.databinding.DataBindingUtil

import android.view.View
import android.widget.ImageView
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
        holder.binding.executePendingBindings()
    }

    companion object {
        @BindingAdapter("android:imageHref")
        @JvmStatic
        fun loadImage(factImageView: ImageView, imageHref: String?) {
            if (imageHref != "") {
                Picasso.get()
                        .load(imageHref)
                        .into(factImageView, object : Callback {
                            override fun onSuccess() {
                                //loadingProgressBar.setVisibility(View.GONE)
                            }

                            override fun onError(e: Exception) {
                                //loadingProgressBar.setVisibility(View.GONE)
                                factImageView.visibility = View.GONE
                            }

                        })
            } else {
                factImageView.visibility = View.GONE
            }
        }
    }

    class FactViewHolder(val binding: FactsListItemBinding) : RecyclerView.ViewHolder(binding.root)

}