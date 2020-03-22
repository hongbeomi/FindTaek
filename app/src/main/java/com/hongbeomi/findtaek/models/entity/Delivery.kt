package com.hongbeomi.findtaek.models.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author hongbeomi
 */

@Entity(tableName = "delivery")
data class Delivery (
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    val fromName: String,
    val fromTime: String,
    val toName: String,
    val toTime: String,
    val carrierId: String,
    val carrierName: String,
    val productName: String,
    val trackId: String,
    val status: String
)