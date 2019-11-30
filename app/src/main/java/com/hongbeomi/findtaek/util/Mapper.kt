package com.hongbeomi.findtaek.util

interface Mapper<in E, T> {

    abstract fun mapFrom(by: E): T

}