package com.hongbeomi.findtaek.view.ui.add

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.hongbeomi.findtaek.R
import com.hongbeomi.findtaek.databinding.ActivityAddBinding
import com.hongbeomi.findtaek.extension.receiveIntentFromMainActivity
import com.hongbeomi.findtaek.extension.unRevealActivity
import com.hongbeomi.findtaek.util.carrierIdMap
import kotlinx.android.synthetic.main.activity_add.*
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
        setDropDownMenu()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        unRevealActivity(addActivityLayout, revealX, revealY)
    }

    private fun setDropDownMenu() {
        val adapter: ArrayAdapter<String> = ArrayAdapter(
            this,
            R.layout.dropdown_menu_popup_item,
            carrierIdMap.keys.toTypedArray()
        )

        exposed_dropdown.apply {
            setAdapter(adapter)
            setOnItemClickListener { _, _, _, _ ->
                exposed_dropdown.isEnabled = false
            }
        }
    }

}