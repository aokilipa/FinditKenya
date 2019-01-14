package ke.co.citesoft.finditkenya.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import androidx.recyclerview.widget.RecyclerView
import ke.co.citesoft.finditkenya.R
import ke.co.citesoft.finditkenya.data.model.Items
import kotlinx.android.synthetic.main.item_main_menu.view.*
import java.util.ArrayList

class MainActivityAdapter(val items: List<Items>, val clickListener: (Items) -> Unit): RecyclerView.Adapter<MainActivityAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainActivityAdapter.MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_main_menu,parent,false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MainActivityAdapter.MyViewHolder, position: Int) {

        (holder as MyViewHolder).bind(items[position],clickListener)
    }


    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        //val tvTitle = itemView.tvTitle
        //val mainLayout = itemView.layout_view

        fun bind(item: Items, clickListener: (Items) -> Unit) {
            itemView.tvTitle.text = item.Title
            itemView.setOnClickListener { clickListener(item)}
        }
    }
}