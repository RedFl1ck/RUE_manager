package com.example.reumanager.ui.accounting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reumanager.data.model.Employee
import com.example.reumanager.data.model.NewEmployee
import com.example.reumanager.databinding.FragmentAccountingBinding
import com.example.reumanager.ui.qualification.QualificationOfEmployeesListAdapter

class PersonalAccounting : Fragment() {

    private var _binding: FragmentAccountingBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountingBinding.inflate(inflater, container, false)
        val view = binding.root

        // Recyclerview
        val adapter = PersonalAccountingListAdapter(this@PersonalAccounting, this.requireContext())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter.setData(listOf(
            NewEmployee("Мышкина", "Илона", "Семеновна", "среднее профессиональное образование", "1 год"),
            NewEmployee("Красильникова", "Нева", "Аркадьевна", "высшее образование - бакалавриат", "2 года"),
            NewEmployee("Дьячкова", "Эллина", "Авдеевна", "среднее общее образование", "6 месяцев"),
            NewEmployee("Васильева", "Елена", "Богуславовна", "высшее образование - специалитет", "6 лет"),
            NewEmployee("Смирнова", "Радмила", "Владимировна", "среднее профессиональное образование", "2 года"),
            NewEmployee("Хохлова", "Анита", "Тарасовна", "высшее образование - подготовка кадров высшей квалификации", "15 лет")
        ))

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}