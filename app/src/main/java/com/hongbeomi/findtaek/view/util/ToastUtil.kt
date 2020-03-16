package com.selectstar.hwshin.cachemission.util

import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import androidx.annotation.StringRes
import com.selectstar.hwshin.cachemission.CashMissionApplication
import com.selectstar.hwshin.cachemission.R
import kotlinx.android.synthetic.main.toast_app.view.*

class ToastUtil {

    companion object {
        /**
         * Show Toast with given context (if not null) or application context for duration of
         * [Toast.LENGTH_SHORT]. It is generally safe to not provide any context to this function,
         * since the application context is initialized most of the times. But in some special cases
         * you might need to supply context to this method.
         */
        @JvmStatic
        fun showShort(@StringRes stringRes: Int, ctx: Context? = null) {
            val context = ctx ?: CashMissionApplication.applicationCtx
            Toast.makeText(context, stringRes, Toast.LENGTH_SHORT).show()
        }

        /**
         * Show Toast with given context (if not null) or application context for duration of
         * [Toast.LENGTH_SHORT]. It is generally safe to not provide any context to this function,
         * since the application context is initialized most of the times. But in some special cases
         * you might need to supply context to this method.
         */
        @JvmStatic
        fun showShort(string: String, ctx: Context? = null) {
            val context = ctx ?: CashMissionApplication.applicationCtx
            Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
        }

        /**
         * Show Toast with given context (if not null) or application context for duration of
         * [Toast.LENGTH_SHORT]. It is generally safe to not provide any context to this function,
         * since the application context is initialized most of the times. But in some special cases
         * you might need to supply context to this method.
         */
        @JvmStatic
        fun showShort(charSequence: CharSequence, ctx: Context? = null) {
            val context = ctx ?: CashMissionApplication.applicationCtx
            Toast.makeText(context, charSequence, Toast.LENGTH_SHORT).show()
        }

        /**
         * Show Toast with given context (if not null) or application context for duration of
         * [Toast.LENGTH_LONG]. It is generally safe to not provide any context to this function,
         * since the application context is initialized most of the times. But in some special cases
         * you might need to supply context to this method.
         */
        @JvmStatic
        fun showLong(@StringRes stringRes: Int, ctx: Context? = null) {
            val context = ctx ?: CashMissionApplication.applicationCtx
            Toast.makeText(context, stringRes, Toast.LENGTH_LONG).show()
        }

        /**
         * Show Toast with given context (if not null) or application context for duration of
         * [Toast.LENGTH_LONG]. It is generally safe to not provide any context to this function,
         * since the application context is initialized most of the times. But in some special cases
         * you might need to supply context to this method.
         */
        @JvmStatic
        fun showLong(string: String, ctx: Context? = null) {
            val context = ctx ?: CashMissionApplication.applicationCtx
            Toast.makeText(context, string, Toast.LENGTH_LONG).show()
        }

        /**
         * Show Toast with given context (if not null) or application context for duration of
         * [Toast.LENGTH_LONG]. It is generally safe to not provide any context to this function,
         * since the application context is initialized most of the times. But in some special cases
         * you might need to supply context to this method.
         */
        @JvmStatic
        fun showLong(charSequence: CharSequence, ctx: Context? = null) {
            val context = ctx ?: CashMissionApplication.applicationCtx
            Toast.makeText(context, charSequence, Toast.LENGTH_LONG).show()
        }

        fun showShortThemed(string: String) {
            Toast(CashMissionApplication.applicationCtx).apply {
                view = LayoutInflater.from(CashMissionApplication.applicationCtx)
                    .inflate(R.layout.toast_app, null).apply {
                        textView_toast.text = string
                    }
            }.show()
        }
    }
}