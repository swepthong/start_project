package com.swepthong.start.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.swepthong.start.base.BaseBindingAdapter
import com.swepthong.start.base.DataBoundViewHolder
import com.swepthong.start.databinding.ItemGankBinding
import com.swepthong.start.injection.ActivityContext
import com.swepthong.start.model.Gank
import javax.inject.Inject

/**
 * Created by xiangrui on 17-10-17.
 */
class HomeAdapter @Inject constructor(@ActivityContext
                                      private val context: Context)
    : BaseBindingAdapter<ItemGankBinding>() {

    var list = listOf<Gank>()

    internal fun setList(list: List<Gank>) {
        this.list = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataBoundViewHolder<ItemGankBinding> {
        return DataBoundViewHolder(
                ItemGankBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: DataBoundViewHolder<ItemGankBinding>, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.binding.item = list[position]
        holder.binding.executePendingBindings()
    }
}