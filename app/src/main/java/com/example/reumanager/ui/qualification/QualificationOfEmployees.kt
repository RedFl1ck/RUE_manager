package com.example.reumanager.ui.qualification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reumanager.data.model.Employee
import com.example.reumanager.databinding.FragmentEmployeesBinding

class QualificationOfEmployees : Fragment() {

    private var _binding: FragmentEmployeesBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //private lateinit var mCocktailViewModel: CocktailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmployeesBinding.inflate(inflater, container, false)
        val view = binding.root

        // Recyclerview
        val adapter = QualificationOfEmployeesListAdapter(this@QualificationOfEmployees, this.requireContext())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter.setData(listOf(
            Employee("Третьяков", "Кондрат", "Рубенович"),
            Employee("Рожков", "Тимофей", "Ярославович"),
            Employee("Рыбаков", "Рудольф", "Вячеславович"),
            Employee("Кошелев", "Илья", "Якунович"),
            Employee("Григорьев", "Матвей", "Тарасович"),
            Employee("Чернов", "Эрнест", "Созонович")
        ))

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}