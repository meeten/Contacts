package com.example.contacts.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact_table")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val contactId: Long = 0L,

    @ColumnInfo(name = "contact_name")
    var contactName: String = "",

    @ColumnInfo(name = "contact_phone")
    var contactPhone: String = "",

    @ColumnInfo("contact_email")
    var contactEmail: String = "",

    @ColumnInfo("contact_address")
    var contactAddress: String = ""
)
