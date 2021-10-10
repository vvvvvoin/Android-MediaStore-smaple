package com.example.readmediafile

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedListAdapterCallback

class MediaStoreImageListCallback(adapter: RecyclerView.Adapter<*>) : SortedListAdapterCallback<MediaStoreImage>(adapter) {

    override fun compare(o1: MediaStoreImage?, o2: MediaStoreImage?): Int {
        if (o1 == null) {
            return 1
        }

        if (o2 == null) {
            return -1
        }

        return -o1.dateTaken.compareTo(o2.dateTaken)
    }

    override fun areContentsTheSame(oldItem: MediaStoreImage?, newItem: MediaStoreImage?): Boolean {
        return false
    }

    override fun areItemsTheSame(item1: MediaStoreImage?, item2: MediaStoreImage?): Boolean {
        return item1?.id == item2?.id
    }
}
