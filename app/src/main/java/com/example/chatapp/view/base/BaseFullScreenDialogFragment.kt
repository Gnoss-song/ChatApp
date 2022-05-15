package com.example.chatapp.view.base

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.example.chatapp.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

open class BaseFullScreenDialogFragment : BottomSheetDialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullBottomSheetDialog)
    }

    override fun onStart() {
        super.onStart()
        dialog?.also {
            val bottomSheet = it.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
            requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
            val behavior = BottomSheetBehavior.from(bottomSheet)
            behavior.peekHeight = resources.displayMetrics.heightPixels
            view?.requestLayout()
        }
    }
}