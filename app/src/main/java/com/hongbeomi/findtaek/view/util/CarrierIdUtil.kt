package com.hongbeomi.findtaek.view.util

/**
 * @author hongbeomi
 */

class CarrierIdUtil {

    companion object {
        private val carrierIdMap = linkedMapOf(
            "CJ대한통운" to "kr.cjlogistics",
            "로젠택배" to "kr.logen",
            "한진택배" to "kr.hanjin",
            "우체국 택배" to "kr.epost",
            "CU 편의점택배" to "kr.cupost",
            "GS Postbox택배" to "kr.cvsnet",
            "CWAY (Woori Express)" to "kr.cway",
            "롯데택배" to "kr.lotte",
            "대신택배" to "kr.daesin",
            "한의사랑택배" to "kr.hanips",
            "합동택배" to "kr.hdexp",
            "홈픽" to "kr.homepick",
            "한서호남택배" to "kr.honamlogis",
            "일양로지스" to "kr.ilyanglogis",
            "경동택배" to "kr.kdexp",
            "건영택배" to "kr.kunyoung",
            "천일택배" to "kr.chunilps",
            "DHL" to "de.dhl",
            "SLX" to "kr.slx",
            "TNT" to "nl.tnt",
            "EMS" to "un.upu.ems",
            "Fedex" to "us.fedex",
            "UPS" to "us.ups",
            "USPS" to "us.usps"
        )

        fun convertId(carrierName: String) = carrierIdMap[carrierName]

        fun getCarrierKeys() = carrierIdMap.keys
    }

}