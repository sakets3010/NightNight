package com.example.nightnight.helper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nightnight.R
import com.example.nightnight.convertDurationToFormatted
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_sleep.view.*
import java.text.SimpleDateFormat
import java.util.*

class SleepAdapter(private val sleep: List<Sleep>) : RecyclerView.Adapter<SleepAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_sleep,parent,false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int = sleep.size

    private fun getDateTime(s: Long): String? {
        return try {
            val sdf = SimpleDateFormat("EEE,hh:mmaa", Locale.getDefault())
            val netDate = Date(s)
            sdf.format(netDate)
        } catch (e: Exception) {
            e.toString()
        }
    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        val item = sleep[position]
        val res = holder.itemView.context.resources
        holder.startTime.text = getDateTime(item.startTime)
        holder.endTime.text = getDateTime(item.endTime)
        holder.duration.text= convertDurationToFormatted(item.startTime,item.endTime,res)
        holder.rating.text=item.sleepRating.toString()
        val img = when(holder.rating.text.toString()){
            "1" -> R.drawable.ic_noun_sad_face_2571147
            "2" -> R.drawable.ic_noun_sad_face_2571156
            "3" -> R.drawable.ic_noun_emotionless_face_2571164
            "4" -> R.drawable.ic_noun_slightly_smiley_face_2571149
            "5" -> R.drawable.ic_noun_smiley_face_2571154
            else -> R.drawable.ic_baseline_help_24
        }
        holder.emotion.setImageResource(img)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val startTime: TextView =itemView.start_time_text
        val endTime: TextView =itemView.end_time_text
        val duration:TextView = itemView.duration_text
        val rating:TextView = itemView.sleep_rating_text
        val emotion:ImageView=itemView.rating_image
    }


}