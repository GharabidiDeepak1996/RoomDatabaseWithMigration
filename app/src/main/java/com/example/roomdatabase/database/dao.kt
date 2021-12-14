package com.example.roomdatabase.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.roomdatabase.UserData

@Dao
interface UserDataDao {

    // @Insert(onConflict = nConflictStrategy.REPLACE) -->
    // @Insert(onConflict = OnConflictStrategy.IGNORE) -->
    // @Insert(onConflict = OnConflictStrategy.REPLACE) -->
    // @Insert(onConflict = OnConflictStrategy.FAIL) -->
    // @Insert(onConflict = OnConflictStrategy.ROLLBACK) -->
    @Insert
    fun insert(data: UserData)

    @Query("SELECT * FROM userdataT")
     fun getAll(): List<UserData>

}