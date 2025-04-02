package com.example.contacts.models

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ContactDao {
    @Insert
    suspend fun insert(contact: Contact)

    @Update
    suspend fun update(contact: Contact)

    @Delete
    suspend fun delete(contact: Contact)

    @Query("SELECT * FROM contact_table WHERE contactId=:key")
    suspend fun get(key: Long): Contact?

    @Query("SELECT * FROM contact_table ORDER BY contactId DESC")
    fun getAll(): LiveData<List<Contact>>
}