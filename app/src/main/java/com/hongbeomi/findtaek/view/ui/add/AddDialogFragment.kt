package com.hongbeomi.findtaek.view.ui.add

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.google.android.material.chip.Chip
import com.hongbeomi.findtaek.R
import com.hongbeomi.findtaek.databinding.FragmentAddBinding
import com.hongbeomi.findtaek.view.ui.base.BaseBottomSheetDialogFragment
import com.hongbeomi.findtaek.view.util.CarrierIdUtil
import com.hongbeomi.findtaek.view.util.ToastUtil
import org.koin.androidx.viewmodel.ext.android.getViewModel

class AddDialogFragment : BaseBottomSheetDialogFragment() {

    private lateinit var viewModel: AddFragmentViewModel
    private lateinit var binding: FragmentAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()
        with(viewModel) {
            deliveryResponse.observe(::getLifecycle) {
                it?.let {
                    insert(it)
                    hideLoading()
                    ToastUtil.showLong(R.string.add_new_delivery_insert_complete_toast)
                    dismiss()
                }
            }
            onCloseEvent.observe(::getLifecycle) { dismiss() }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return (DataBindingUtil.inflate<FragmentAddBinding>(
            inflater,
            R.layout.fragment_add,
            container,
            false
        ).also { binding = it }.root)
            .also {
                binding.lifecycleOwner = viewLifecycleOwner
                binding.vm = viewModel
                setChipGroup()
            }
    }

    private fun setChipGroup() {
        for (name in CarrierIdUtil.getCarrierKeys()) {
            binding.chipGroupAddCarrierName.addView(
                Chip(activity).apply {
                    text = name
                    isCheckable = true
                    isClickable = true
                    elevation = 4f
                    setIconStartPaddingResource(R.dimen.chip_padding_start)
                    checkedIcon =
                        resources.getDrawable(R.drawable.ic_outline_local_shipping_24)
                    chipBackgroundColor =
                        resources.getColorStateList(R.color.color_add_chip_bg)
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