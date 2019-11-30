package com.hongbeomi.findtaek.view.ui.add

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.hongbeomi.findtaek.R
import com.hongbeomi.findtaek.databinding.ActivityAddBinding
import kotlinx.android.synthetic.main.activity_add.*
import org.jetbrains.anko.toast
import org.koin.androidx.viewmodel.ext.android.getViewModel

class AddActivity : AppCompatActivity() {
    private lateinit var addVM: AddViewModel

    private lateinit var binding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add)

//        setContentView(R.layout.activity_add)
        binding.vm = getViewModel()

//        addVM = getViewModel()
        binding.vm.also {
            it?.observeToast(this) { message -> toast(message) }
            it?.observeFinish(this) { finish() }
        }
        binding.lifecycleOwner = this
    }

}