package com.kostyakilroy.daysleft

import android.icu.util.Calendar
import android.icu.util.TimeZone
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.kostyakilroy.daysleft.Data.Dates
import java.util.concurrent.TimeUnit

class DatesListAdapter : RecyclerView.Adapter<DatesListAdapter.ViewHolder>() {
    private lateinit var visibilityInterface: VisabilityInterface

    fun setVisibilityInterface(visibilityInterface: VisabilityInterface) {
        this.visibilityInterface = visibilityInterface
    }

    var data = listOf<Dates>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = data[position]
        val today = Calendar.getInstance().time
        val offsetDateTime = TimeZone.getDefault().getOffset(today.time)
        val date = current.endDate.time - today.time - offsetDateTime.toLong()
        val daysLeft = TimeUnit.MILLISECONDS.toDays(date)
        holder.dateName.text = current.dateName
        holder.dateDate.text = holder.itemView.context.getString(R.string.days_left, daysLeft)

        holder.card.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_mainFragment_to_editFragment, bundleOf("id" to current.dateId))
        }

        holder.card.setOnLongClickListener { view ->
//            val application = holder.itemView
//            val toolbar = application.findViewById<MaterialToolbar>(R.id.myToolbar)
//            toolbar.visibility = View.VISIBLE
            visibilityInterface.onClick()
            return@setOnLongClickListener true
        }
    }

    override fun getItemCount() = data.size

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val card: CardView = itemView.findViewById(R.id.card_view)
        val dateName: TextView = itemView.findViewById(R.id.date_name)
        val dateDate: TextView = itemView.findViewById(R.id.date_date)
    }

}