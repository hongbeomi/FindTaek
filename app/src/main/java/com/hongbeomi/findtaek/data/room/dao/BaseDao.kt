package com.hongbeomi.findtaek.data.room.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

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