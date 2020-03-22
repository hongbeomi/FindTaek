package com.hongbeomi.findtaek.data.room.dao

import androidx.room.*

/**
 * @author hongbeomi
 */

abstract class BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(vararg t: T)

    @Update
    abstract suspend fun updateAll(ts: List<T>)

    @Delete
    abstract suspend fun delete(vararg t: T)

}