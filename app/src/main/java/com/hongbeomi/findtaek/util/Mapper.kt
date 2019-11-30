package com.hongbeomi.findtaek.util

interface Mapper<in E, T> {

    fun mapFrom(by: E): T

}