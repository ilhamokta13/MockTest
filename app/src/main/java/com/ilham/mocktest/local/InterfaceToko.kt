package com.ilham.mocktest.local

import android.provider.ContactsContract
import androidx.room.*
import com.ilham.mocktest.model.DataToko

@Dao
interface InterfaceToko {
    @Insert
    fun insertToko(toko: DataToko)

    @Query("SELECT * FROM DataToko ORDER BY nama ASC ")
    fun getTokoASC(): List<DataToko>

    @Query("SELECT * FROM DataToko ORDER BY nama DESC")
    fun getTokoDESC() : List<DataToko>

    @Delete
    fun deleteToko(note: DataToko)

    @Update
    fun updateToko(note: DataToko)

}