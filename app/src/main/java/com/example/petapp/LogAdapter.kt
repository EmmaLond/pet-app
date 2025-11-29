package com.example.petapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LogAdapter : RecyclerView.Adapter<LogAdapter.LogViewHolder>() {

    private var logs = listOf<Log>()

    fun submitList(newLogs: List<Log>) {
        logs = newLogs
        notifyDataSetChanged()
    }

    class LogViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val activityText: TextView = view.findViewById(R.id.activityText)
        val timeText: TextView = view.findViewById(R.id.timeText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.log_item, parent, false)
        return LogViewHolder(view)
    }

    override fun onBindViewHolder(holder: LogViewHolder, position: Int) {
        val log = logs[position]
        holder.activityText.text = log.activity
        holder.timeText.text = log.timeOccurred.toString()
    }

    override fun getItemCount(): Int = logs.size
}
