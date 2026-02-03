package com.tanish.coderrootstask

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.tanish.coderrootstask.databinding.CustomdialogBinding
import com.tanish.coderrootstask.databinding.FragmentMenuPageBinding
import com.tanish.recyclerviewpractice.MyAdapter

class MenuPage : Fragment(), MenuData {

    lateinit var binding: FragmentMenuPageBinding
    private lateinit var adapter: MyAdapter
    private var menuList = arrayListOf<ModelClass>()
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuPageBinding.inflate(layoutInflater)

        binding.ADdItem.setOnClickListener {
            showCustomDialog(-1)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        mainActivity=activity as MainActivity
        menuList = mainActivity.menuListGlobal

        adapter = MyAdapter(menuList, this)
        binding.listView.layoutManager = LinearLayoutManager(mainActivity)
        binding.listView.adapter = adapter
    }

    private fun showCustomDialog(position: Int) {
        val dialog = Dialog(requireContext())
        val dialogBinding = CustomdialogBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )

        if (position > -1) {
            dialogBinding.btnAction.text = "Update"
            dialogBinding.EditText.setText(menuList[position].itemName)
            dialogBinding.EditPrice.setText(menuList[position].itemPrice.toString())
        } else {
            dialogBinding.btnAction.text = "Add"
            dialogBinding.EditText.setText("")
            dialogBinding.EditPrice.setText("")
        }

        dialogBinding.btnAction.setOnClickListener {

            val name = dialogBinding.EditText.text.toString()
            val price = dialogBinding.EditPrice.text.toString()
            if (name.isEmpty()) {
                dialogBinding.EditText.error = "enter the item "
            } else if (price.trim().isEmpty()) {
                dialogBinding.EditPrice.error = "enter the amount"
            } else {


                val item = ModelClass(name, price.toDouble())

                if (position > -1) {
                    menuList[position] = item

                } else {
                    menuList.add(item)

                }

                adapter.notifyDataSetChanged()
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    override fun item(position: Int) {
        showCustomDialog(position)
    }

    override fun price(rupes: Double) {}
}
