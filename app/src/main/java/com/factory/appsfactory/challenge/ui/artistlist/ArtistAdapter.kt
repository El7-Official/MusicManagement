package com.factory.appsfactory.challenge.ui.artistlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.factory.appsfactory.challenge.databinding.ItemArtistBinding
import com.factory.appsfactory.challenge.ui.base.BaseAdapter
import com.factory.appsfactory.core.domain.Artist

class ArtistAdapter : BaseAdapter<Artist>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Artist>() {
        override fun areItemsTheSame(oldItem: Artist, newItem: Artist): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Artist, newItem: Artist): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    override val differ = AsyncListDiffer(this, diffCallback)

    override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemArtistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArtistViewHolder(binding)
    }

    inner class ArtistViewHolder(private val binding: ItemArtistBinding) :
        RecyclerView.ViewHolder(binding.root), Binder<Artist> {
        override fun bind(item: Artist) {
            binding.apply {
                txtViewName.text = item.name
                root.setOnClickListener {
                    onItemClickListener?.let { itemClick ->
                        itemClick(item)
                    }
                }
                txtViewLink.text = item.url
            }
        }
    }
}
