package com.hongbeomi.findtaek.repository

/**
 * @author hongbeomi
 */
interface Mapper<in E, T> {

    fun mapFrom(by: E): T

}