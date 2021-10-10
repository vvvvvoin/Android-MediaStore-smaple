package com.example.readmediafile

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList

class MediaImageAdapter : RecyclerView.Adapter<ImageViewHolder>() {

    private val sortedList = SortedList(MediaStoreImage::class.java, MediaStoreImageListCallback(this))

    fun setItem(items: List<MediaStoreImage>) {
        sortedList.replaceAll(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder.newInstance(parent)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(sortedList.get(position))
    }

    override fun getItemCount(): Int {
        return sortedList.size()
    }
}
