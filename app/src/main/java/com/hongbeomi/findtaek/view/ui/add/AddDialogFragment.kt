package com.hongbeomi.findtaek.view.ui.add

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import com.google.android.material.chip.Chip
import com.hongbeomi.findtaek.R
import com.hongbeomi.findtaek.databinding.FragmentAddBinding
import com.hongbeomi.findtaek.view.ui.base.BaseBottomSheetDialogFragment
import com.hongbeomi.findtaek.view.util.CarrierIdUtil
import com.hongbeomi.findtaek.view.util.ToastUtil
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author hongbeomi
 */

class AddDialogFragment : BaseBottomSheetDialogFragment() {

    private val viewModel by viewModel<AddFragmentViewModel>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding<FragmentAddBinding>(
        inflater, R.layout.fragment_add, container
    ).apply {
        vm = viewModel
        lifecycleOwner = this@AddDialogFragment
        setChipGroup(this)
        initObserve()
    }.root

    private fun initObserve() {
        with(viewModel) {
            deliveryResponse.observe(::getLifecycle) {
                it?.let {
                    insert(it)
                    ToastUtil.showLong(R.string.add_new_delivery_insert_complete_toast)
                    dismiss()
                }
            }
            onCloseEvent.observe(::getLifecycle) { dismiss() }
        }
    }

    private fun setChipGroup(binding: FragmentAddBinding) {
        for (name in CarrierIdUtil.getCarrierKeys()) {
            binding.chipGroupAddCarrierName.addView(
                Chip(activity).apply {
                    text = name
                    isCheckable = true
                    isClickable = true
                    setTextColor(Color.WHITE)
                    setEnsureMinTouchTargetSize(false)
                    checkedIcon = null
                    chipBackgroundColor = resources.getColorStateList(R.color.color_add_chip_bg)
                }
            )
        }
        binding.chipGroupAddCarrierName.setOnCheckedChangeListener { group, checkedId ->
            group.findViewById<Chip>(checkedId)?.let {
                viewModel.carrierName.postValue(it.text.toString())
            } ?: viewModel.carrierName.postValue(null)
        }
    }

}