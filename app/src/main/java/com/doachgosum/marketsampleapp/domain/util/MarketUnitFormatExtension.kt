package com.doachgosum.marketsampleapp.domain.util

import java.text.DecimalFormat
import kotlin.math.absoluteValue


fun Double.toMarketFormat(): String {
    return try {
        when {
            this.absoluteValue > 100 -> this
                .toLong() // 소수점 버림
                .let { DecimalFormat("#,###").format(it) }

            else -> DecimalFormat("#,##0.00").format(this)
        }
    } catch (e: Exception) {
        ""
    }
}

/***
 * Pair.first() -> 값
 * Pair.second() -> 단위
 * */
fun Double.toMarketVolumeFormat(): Pair<String, String?> {
    return try {
        when {
            // 임의 추가, 백만 단위가 넘으면 '백만' 을 붙여 표시
            this.absoluteValue >= 1000000 -> (this/1000000)
                .toLong()
                .let {
                    DecimalFormat("#,###").format(it) to "백만"
                }

            else -> this
                .toLong()
                .let {
                    DecimalFormat("#,###").format(this) to null
                }
        }

    } catch (e: Exception) {
        "" to null
    }
}