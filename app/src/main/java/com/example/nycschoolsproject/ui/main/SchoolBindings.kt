package com.example.nycschoolsproject.ui.main

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.nycschoolsproject.model.SchoolItem
import com.example.nycschoolsproject.viewmodel.SchoolListViewModel

/**
 * Binding methods added to update UI items from layout files.
 */

@BindingAdapter("items", "viewmodel", requireAll = true)
fun RecyclerView.addItems(items : List<SchoolItem>?, viewmodel: SchoolListViewModel) {

    if (this.adapter == null) {
        val listAdapter = SchoolListAdapter(items ?: emptyList(), viewmodel)
        adapter = listAdapter
        addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    } else {
        (adapter as? SchoolListAdapter)?.let {
            if (items != null) {
                it.items = items
            }
            it.notifyDataSetChanged()
        }
    }
}

@BindingAdapter("onCancelRefresh")
fun SwipeRefreshLayout.onCancelRefresh(cancel: Boolean) {
    if (cancel) {
        isRefreshing = false
    }
}