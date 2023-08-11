package com.pushparaj.assignment.nycschools.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pushparaj.assignment.nycschools.R
import com.pushparaj.assignment.nycschools.model.School
import com.pushparaj.assignment.nycschools.ui.OnItemClickListener

class SchoolListAdapter(var schools: List<School>,  val listener: OnItemClickListener) : RecyclerView.Adapter<SchoolListAdapter.SchoolViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchoolViewHolder {
          return SchoolViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.school_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return schools.size
    }

    override fun onBindViewHolder(holder: SchoolViewHolder, position: Int) {
       val school = schools.get(position)
        holder.nameView.text = school.school_name
        holder.summaryView.text = school.overview_paragraph
    }

    inner class SchoolViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position)
                }
            }
        }
        val nameView: TextView = itemView.findViewById(R.id.name)
        val summaryView: TextView = itemView.findViewById(R.id.summary)
    }

    fun setSchoolList(list: List<School>){
        schools = list
        notifyDataSetChanged()
    }

}