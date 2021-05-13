package com.example.reumanager.ui.accounting

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.reumanager.R
import com.example.reumanager.data.model.NewEmployee
import com.example.reumanager.open.NewEmployeeShow
import kotlinx.android.synthetic.main.row_new_employee.view.*

class PersonalAccountingListAdapter constructor(private val activity: Fragment, private val context: Context)
    : RecyclerView.Adapter<PersonalAccountingListAdapter.MyViewHolder>() {

    private var newEmployeeList = emptyList<NewEmployee>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.row_new_employee, parent, false))
    }

    override fun getItemCount(): Int {
        return newEmployeeList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = newEmployeeList[position]
        holder.itemView.new_employee_name_txt.text = currentItem.name
        holder.itemView.new_employee_surname_txt.text = currentItem.surname
        holder.itemView.new_employee_patronymic_txt.text = currentItem.patronymic
        holder.itemView.new_employee_education_txt.text = currentItem.education
        holder.itemView.new_employee_experience_txt.text = currentItem.experience

        holder.itemView.setOnClickListener {
            val arg = Intent(activity.requireActivity(), NewEmployeeShow::class.java)
            arg.putExtra(NewEmployeeShow.NEW_EMPLOYEE, currentItem)
            ContextCompat.startActivity(activity.requireContext(), arg, null)
        }
    }

    fun setData(newEmployee: List<NewEmployee>){
        this.newEmployeeList = newEmployee
        notifyDataSetChanged()
    }
}