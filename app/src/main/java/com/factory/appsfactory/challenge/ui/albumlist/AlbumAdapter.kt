package com.factory.appsfactory.challenge.ui.albumlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.factory.appsfactory.challenge.databinding.ItemAlbumBinding
import com.factory.appsfactory.challenge.ui.base.BaseAdapter
import com.factory.appsfactory.core.domain.Album

class AlbumAdapter : BaseAdapter<Album>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Album>() {
        override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    override val differ = AsyncListDiffer(this, diffCallback)

    override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlbumViewHolder(binding)
    }

    inner class AlbumViewHolder(private val binding: ItemAlbumBinding) :
        RecyclerView.ViewHolder(binding.root), Binder<Album> {
        override fun bind(item: Album) {
            binding.apply {
                txtViewName.text = item.name
                root.setOnClickListener {
                    onItemClickListener?.let { itemClick ->
                        itemClick(item)
                    }
                }
                txtViewPlayCount.text = "${item.playCount}"
                txtViewLink.text = item.url
            }
        }
    }
}
