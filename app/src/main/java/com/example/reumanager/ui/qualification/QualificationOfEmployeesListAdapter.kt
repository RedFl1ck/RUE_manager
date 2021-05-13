package com.example.reumanager.ui.qualification

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.reumanager.R
import com.example.reumanager.data.model.Employee
import com.example.reumanager.open.EmployeeShow
import kotlinx.android.synthetic.main.row_employee.view.*

class QualificationOfEmployeesListAdapter constructor(private val activity: Fragment, private val context: Context)
    : RecyclerView.Adapter<QualificationOfEmployeesListAdapter.MyViewHolder>() {

    private var employeeList = emptyList<Employee>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.row_employee, parent, false))
    }

    override fun getItemCount(): Int {
        return employeeList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = employeeList[position]
        holder.itemView.employee_name_txt.text = currentItem.name
        holder.itemView.employee_surname_txt.text = currentItem.surname
        holder.itemView.employee_patronymic_txt.text = currentItem.patronymic

        holder.itemView.setOnClickListener {
            val arg = Intent(activity.requireActivity(), EmployeeShow::class.java)
            arg.putExtra(EmployeeShow.EMPLOYEE, currentItem)
            startActivity(activity.requireContext(), arg, null)
        }
    }

    fun setData(employee: List<Employee>){
        this.employeeList = employee
        notifyDataSetChanged()
    }
}