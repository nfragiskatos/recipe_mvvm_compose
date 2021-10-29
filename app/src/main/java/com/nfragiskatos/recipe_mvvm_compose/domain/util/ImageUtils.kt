package com.nfragiskatos.recipe_mvvm_compose.domain.util

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.nfragiskatos.recipe_mvvm_compose.R

const val DEFAULT_RECIPE_IMAGE = R.drawable.empty_plate


@Composable
fun loadPictures(url: String, @DrawableRes defaultImage: Int) : MutableState<Bitmap?> {

    val bitmapState: MutableState<Bitmap?> = remember {mutableStateOf(null)}

    Glide.with(LocalContext.current)
        .asBitmap()
        .load(defaultImage)
        .into(object: CustomTarget<Bitmap>() {
            override fun onResourceReady(
                resource: Bitmap,
                transition: Transition<in Bitmap>?
            ) {
                bitmapState.value = resource
            }

            override fun onLoadCleared(placeholder: Drawable?) {
                // intentionally blank
            }
        })

    Glide.with(LocalContext.current)
        .asBitmap()
        .load(url)
        .into(object: CustomTarget<Bitmap>() {
            override fun onResourceReady(
                resource: Bitmap,
                transition: Transition<in Bitmap>?
            ) {
                bitmapState.value = resource
            }

            override fun onLoadCleared(placeholder: Drawable?) {
                // intentionally blank
            }
        })

    return bitmapState
}

@Composable
fun loadPictures(@DrawableRes drawable: Int) : MutableState<Bitmap?> {

    val bitmapState: MutableState<Bitmap?> = remember {mutableStateOf(null)}

    Glide.with(LocalContext.current)
        .asBitmap()
        .load(drawable)
        .into(object: CustomTarget<Bitmap>() {
            override fun onResourceReady(
                resource: Bitmap,
                transition: Transition<in Bitmap>?
            ) {
                bitmapState.value = resource
            }

            override fun onLoadCleared(placeholder: Drawable?) {
                // intentionally blank
            }
        })

    return bitmapState
}