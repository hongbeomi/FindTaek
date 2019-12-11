package com.hongbeomi.findtaek.repository.util

/**
 * @author hongbeomi
 */

interface Mapper<in E, T> {

    fun mapFrom(by: E): T

}