package com.kostyakilroy.daysleft

import android.icu.util.Calendar
import android.icu.util.TimeZone
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.kostyakilroy.daysleft.Data.Dates
import java.sql.Time
import java.time.*
import java.time.temporal.ChronoUnit
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

        val layout = when (viewType) {
            IMPORTANCE_1 -> R.layout.recyclerview_item
            IMPORTANCE_2 -> R.layout.recyclerview_item_2
            else -> R.layout.recyclerview_item_3
        }

        val view = LayoutInflater.from(parent.context)
            .inflate(layout, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = data[position]
//        val today = Calendar.getInstance().time
//        val offset = ZoneId.systemDefault().rules.getOffset(Instant.now()).totalSeconds
//        val today = Instant.now()
//        val offsetDateTime = TimeZone.getDefault().getOffset(today)
//        val difference = ChronoUnit.SECONDS.between(today, current.endDate.toInstant()) - offset
//        val date = current.endDate.time - today - offsetDateTime.toLong()
//        val daysLeft = TimeUnit.SECONDS.toHours(difference)

        val today = Instant.now()
        val offset = OffsetTime.now().offset.totalSeconds / 3600
        val hoursLeft = ChronoUnit.HOURS.between(today, current.endDate.toInstant()) - offset
        val totalHours = ChronoUnit.HOURS.between(current.startDate.toInstant(), current.endDate.toInstant())
        val percentage = if (hoursLeft >= 0) (hoursLeft * 100) / totalHours else 0
        val daysLeft = if (hoursLeft >= 0) hoursLeft / 24 + 1 else 0

        when (percentage.toInt()) {
            in 0..20 -> holder.bgImage.setImageResource(R.drawable.phase_1)
            in 21..40 -> holder.bgImage.setImageResource(R.drawable.phase_2)
            in 41..60 -> holder.bgImage.setImageResource(R.drawable.phase_3)
            in 61..80 -> holder.bgImage.setImageResource(R.drawable.phase_4)
            else -> holder.bgImage.setImageResource(R.drawable.phase_5)
        }


        holder.dateName.text = current.dateName
        holder.dateDate.text = holder.itemView.context.getString(R.string.days_left, daysLeft)

        holder.card.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_mainFragment_to_editFragment, bundleOf("id" to current.dateId))
        }

        holder.card.setOnLongClickListener { _ ->
//            val application = holder.itemView
//            val toolbar = application.findViewById<MaterialToolbar>(R.id.myToolbar)
//            toolbar.visibility = View.VISIBLE
            visibilityInterface.onClick()
            return@setOnLongClickListener true
        }
    }

    override fun getItemCount() = data.size

    override fun getItemViewType(position: Int): Int {
        return when (data[position].importance) {
            0 -> IMPORTANCE_1
            1 -> IMPORTANCE_2
            else -> IMPORTANCE_3
        }
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val card: CardView = itemView.findViewById(R.id.card_view)
        val dateName: TextView = itemView.findViewById(R.id.date_name)
        val dateDate: TextView = itemView.findViewById(R.id.date_date)
        val bgImage: ImageView = itemView.findViewById(R.id.imageView)
    }

    companion object {
        private const val IMPORTANCE_1 = 0
        private const val IMPORTANCE_2 = 1
        private const val IMPORTANCE_3 = 2
    }

}