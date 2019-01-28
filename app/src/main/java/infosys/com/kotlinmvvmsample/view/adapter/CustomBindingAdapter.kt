package infosys.com.kotlinmvvmsample.view.adapter

import android.databinding.BindingAdapter
import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso
import infosys.com.kotlinmvvmsample.R

class CustomBindingAdapter {
    companion object {
        // Custom binding adapter for visibility
        @JvmStatic
        @BindingAdapter("visibleGone")
        fun showHide(view: View, show: Boolean) {
            view.visibility = if (show) View.VISIBLE else View.GONE
        }

        /// static binding adapter to load image using picasso
        @BindingAdapter("android:imageHref")
        @JvmStatic
        fun loadImage(factImageView: ImageView, imageHref: String?) {
            if (imageHref != "") {
                Picasso.get()
                        .load(imageHref)
                        .placeholder(R.drawable.no_image_placeholder)
                        .into(factImageView)
            }
        }
    }
}