package com.jahnold.lastfmsample.list.view

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jahnold.lastfmsample.base.extension.bindView
import com.jahnold.lastfmsample.list.R
import com.jahnold.lastfmsample.list.data.ListUiModel
import com.squareup.picasso.Picasso
import io.reactivex.subjects.PublishSubject

class ListViewHolder(item: View, private val clickSubject: PublishSubject<String>): RecyclerView.ViewHolder(item) {

    private val layout: ViewGroup by bindView(R.id.item_layout)
    private val name: TextView by bindView(R.id.item_name)
    private val artist: TextView by bindView(R.id.item_artist)
    private val image: ImageView by bindView(R.id.item_image)

    fun bind(data: ListUiModel) {

        layout.setOnClickListener { clickSubject.onNext(data.uuid) }

        name.text = data.name
        artist.text = data.artist

        Picasso.get().load(data.imageUrl).into(image)
    }
}