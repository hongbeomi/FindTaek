package com.hongbeomi.findtaek.view.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

/**
 * @author hongbeomi
 */

class TimeUtil {

    companion object {
        @JvmStatic
        @SuppressLint("SimpleDateFormat")
        fun getFormatterTime(time: String): String {
            return SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분").format(
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(time)
            )
        }

        @JvmStatic
        fun determineTime(toTimeData: String?) =
            if (toTimeData.isNullOrEmpty()) "정보없음" else toTimeData.substring(0, 10)
    }

}