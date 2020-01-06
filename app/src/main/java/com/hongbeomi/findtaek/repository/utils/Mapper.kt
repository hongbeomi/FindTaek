package com.hongbeomi.findtaek.repository.utils

/**
 * @author hongbeomi
 */

interface Mapper<in E, T> {

    fun mapFrom(by: E): T

}