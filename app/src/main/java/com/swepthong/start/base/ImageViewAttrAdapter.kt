package com.swepthong.start.base

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Created by xiangrui on 17-10-17.
 */
@BindingAdapter("load_asset")
fun loadAsset(imageView: ImageView, id: Int) =
        Glide.with(imageView.context).load(id).into(imageView)

@BindingAdapter("load_image")
fun loadImage(imageView: ImageView, url: String?) =
        Glide.with(imageView.context).load(url)
                .into(imageView)!!