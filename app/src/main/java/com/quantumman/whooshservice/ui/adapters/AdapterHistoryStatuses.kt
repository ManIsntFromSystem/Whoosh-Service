package com.quantumman.whooshservice.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.quantumman.whooshservice.R
import com.quantumman.whooshservice.ui.model.StatusScooterDataItem

class AdapterHistoryStatuses(
    items: MutableList<StatusScooterDataItem>,
    onStatusListener: OnStatusListener,
    onDeleteItemListener: OnDeleteClickListener)
    : RecyclerView.Adapter<AdapterHistoryStatuses.HistoryHolder>() {

    private val mStatusList: MutableList<StatusScooterDataItem> = items
    private val mOnStatusListener = onStatusListener
    private val mOnDeleteListener = onDeleteItemListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_status, parent, false)
        return HistoryHolder(view, mOnStatusListener, mOnDeleteListener)
    }

    fun setData(listStatuses: List<StatusScooterDataItem>) {
        mStatusList.clear()
        mStatusList += listStatuses
        notifyDataSetChanged()
    }

    fun deleteAll() {
        mStatusList.clear()
        notifyItemRangeRemoved(0, itemCount)
    }

    fun deleteStatus(id: Int) {
        mStatusList.removeAt(id)
        notifyItemRemoved(id)
    }

    override fun getItemCount(): Int = if (mStatusList.isNotEmpty()) mStatusList.size else 0

    override fun onBindViewHolder(holder: HistoryHolder, position: Int) {
        holder.bind(mStatusList[position])
    }

    inner class HistoryHolder(
        itemView: View,
        onStatusListener: OnStatusListener,
        onDeleteListener: OnDeleteClickListener
    ) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val tvHistoryScooterName =
            itemView.findViewById<TextView>(R.id.tvHistoryScooterName)
        private val tvHistoryStatusScooter =
            itemView.findViewById<TextView>(R.id.tvHistoryStatusScooter)
        private val tvHistoryComment = itemView.findViewById<TextView>(R.id.tvHistoryComment)
        private val tvHistoryDate = itemView.findViewById<TextView>(R.id.tvHistoryDate)
        private val ivHistoryDeleteStatus =
            itemView.findViewById<ImageView>(R.id.ivHistoryDeleteStatus)
        private var mOnStatusListener: OnStatusListener = onStatusListener
        private var mOnDeleteListener: OnDeleteClickListener = onDeleteListener

        fun bind(status: StatusScooterDataItem) {
            tvHistoryScooterName.text = status.name
            tvHistoryStatusScooter.text = status.status
            tvHistoryComment.text = status.comments
            tvHistoryDate.text = status.date
        }

        init {
            ivHistoryDeleteStatus.setOnClickListener(this)
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) = when (view) {
            is ImageView -> mOnDeleteListener.onDeleteItem(adapterPosition, mStatusList[adapterPosition])
            else -> mOnStatusListener.onStatusRecyclerClick(adapterPosition, mStatusList[adapterPosition])
        }
    }

    interface OnStatusListener {
        fun onStatusRecyclerClick(position: Int, itemStatus: StatusScooterDataItem)
    }

    interface OnDeleteClickListener {
        fun onDeleteItem(position: Int, itemStatus: StatusScooterDataItem)
    }
}