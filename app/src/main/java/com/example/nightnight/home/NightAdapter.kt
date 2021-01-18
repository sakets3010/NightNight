package com.example.nightnight.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.nightnight.R
import com.example.nightnight.convertDurationToFormatted
import com.example.nightnight.db.Night
import kotlinx.android.synthetic.main.list_item_sleep.view.*
import java.text.SimpleDateFormat
import java.util.*

class NightAdapter(private val sleep: List<Night>) : RecyclerView.Adapter<NightAdapter.ViewHolder>() {
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        val item = sleep[position]
        val res = holder.itemView.context.resources
        holder.startTime.text = getDateTime(item.initTime)
        holder.endTime.text = getDateTime(item.endTime)
        holder.duration.text= convertDurationToFormatted(item.initTime,item.endTime,res)
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
        holder.button.setOnClickListener{
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT,"my recent sleep,start time:${holder.startTime.text},duration:${holder.duration.text},rating:${holder.rating.text}/5")
            intent.type = "text/plain"
            val bundle = intent.getBundleExtra(Intent.EXTRA_TEXT)
            startActivity(holder.itemView.context,intent,bundle)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {

        val startTime: TextView = itemView.start_time_text
        val endTime: TextView = itemView.end_time_text
        val duration:TextView = itemView.duration_text
        val rating:TextView = itemView.sleep_rating_text
        val emotion:ImageView = itemView.rating_image
        val button:ImageButton = itemView.shareButton

    }


}