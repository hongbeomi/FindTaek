package com.hongbeomi.findtaek.view.ui.add

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.hongbeomi.findtaek.R
import com.hongbeomi.findtaek.databinding.ActivityAddBinding
import com.hongbeomi.findtaek.extension.receiveIntentFromMainActivity
import com.hongbeomi.findtaek.extension.unRevealActivity
import com.hongbeomi.findtaek.view.util.CarrierIdMap
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_add.view.*
import org.jetbrains.anko.toast
import org.koin.androidx.viewmodel.ext.android.getViewModel

/**
 * @author hongbeomi
 */

class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding

    companion object {
        var revealX: Int = 0
        var revealY: Int = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add)

        binding.vm = getViewModel()
        binding.vm.also {
            it?.observeToast(this) { message -> toast(message) }
            it?.observeFinish(this) { finish() }
        }

        binding.lifecycleOwner = this

        receiveIntentFromMainActivity(savedInstanceState)
        initDropDownMenu()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        unRevealActivity(addActivityLayout, revealX, revealY)
    }

    private fun initDropDownMenu() {
        val adapter: ArrayAdapter<String> = ArrayAdapter(
            this,
            R.layout.dropdown_menu_popup_item,
            CarrierIdMap().getKeys().toTypedArray()
        )
        adapter.setDropDownViewResource(R.layout.dropdown_menu_popup_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                println(parent?.getItemAtPosition(position).toString())
                binding.vm?.carrierName?.value = parent?.getItemAtPosition(position).toString()
            }
        }
    }

}