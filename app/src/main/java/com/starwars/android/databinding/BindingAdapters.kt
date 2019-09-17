package com.starwars.android.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.api.load
import com.starwars.android.Constants

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageFileName: String?) {
    if (!imageFileName.isNullOrEmpty()) {
        // Load image using coil library
        view.load(Constants.IMAGE_ASSETS_URL + imageFileName) {
            crossfade(true)
        }
    }
}
