package com.hongbeomi.findtaek.view.ui.add

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hongbeomi.findtaek.R
import kotlinx.android.synthetic.main.activity_add.*
import org.jetbrains.anko.toast
import org.koin.androidx.viewmodel.ext.android.getViewModel

class AddActivity : AppCompatActivity() {
    private lateinit var addVM: AddViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        addVM = getViewModel()
        addVM.also {
            it.observeToast(this) { message -> toast(message) }
            it.observeFinish(this) { finish() }
        }

        add_button.setOnClickListener {
            // val number = numEt.text.toString().trim()
            // val name = nameEt.text.toString().trim()

            val productName = "물건이름"
            val trackId = "348621627991"
            val carrierName = "CJ대한통운"
            addVM.apply {
                checkTrackIdAndInsertDelivery(productName, carrierName, trackId)
            }
        }
    }

}