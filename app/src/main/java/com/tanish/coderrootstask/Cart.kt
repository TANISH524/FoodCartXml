package com.tanish.coderrootstask

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.tanish.coderrootstask.databinding.FragmentCartBinding
import com.tanish.recyclerviewpractice.MyAdapter

class Cart : Fragment(), MenuData {

    lateinit var binding: FragmentCartBinding
    private val cartList = arrayListOf<ModelClass>()
    private var selectedItem: ModelClass? = null
    private lateinit var cartAdapter: MyAdapter
    private var menuItems = arrayListOf<ModelClass>()

    override fun onCreateView(
        inflater: android.view.LayoutInflater, container: android.view.ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val mainActivity = activity as MainActivity
        menuItems = mainActivity.menuListGlobal// global main activvity wala variable

        //spinner set up kita mai
        val spinnerNames = menuItems.map { it.itemName }
        binding.spinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, spinnerNames)

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedItem = menuItems[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // RecyclerView setup
        cartAdapter = MyAdapter(cartList, this)
        binding.cartRecycler.layoutManager = LinearLayoutManager(mainActivity)
        binding.cartRecycler.adapter = cartAdapter

        // Add button
        binding.btnAdd.setOnClickListener {
            selectedItem?.let {
                cartList.add(it.copy()) // add to cart
                cartAdapter.notifyDataSetChanged()
                updateTotal()
            }
        }
    }

    private fun updateTotal() {
        val total = cartList.sumOf { it.itemPrice }
        binding.totalText.text = "Total : ₹ $total"
    }

    override fun item(position: Int) {
        // Example: Increase price by 10 on item click
        cartList[position].itemPrice += 10
        cartAdapter.notifyItemChanged(position)
        updateTotal()
    }



    override fun price(rupes: Double) {
        binding.totalText.text = "Total : ₹ $rupes"
    }
}
