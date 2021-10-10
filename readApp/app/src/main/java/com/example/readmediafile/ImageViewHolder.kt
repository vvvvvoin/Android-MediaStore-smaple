package com.example.readmediafile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.readmediafile.databinding.ViewHolderImageBinding

class ImageViewHolder(private val binding: ViewHolderImageBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(mediaStoreImage: MediaStoreImage) {
        binding.textViewHolder.text = mediaStoreImage.displayName

        Glide.with(itemView)
            .load(mediaStoreImage.contentUri)
            .into(binding.imageViewHolder)
    }

    companion object {
        fun newInstance(parent: ViewGroup): ImageViewHolder {
            return ImageViewHolder(
                ViewHolderImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }
}
