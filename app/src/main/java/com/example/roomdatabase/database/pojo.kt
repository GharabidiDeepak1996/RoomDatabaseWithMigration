package com.example.roomdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userdataT")
data class UserData(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    var name: String?,
   // var nickname:String?,
    var Gender:String?
        )

/* @PrimaryKey(autoGenerate = true)
   @ColumnInfo(name = "id")
   var id: Long?,

   @ColumnInfo(name = "Name")
   var name: String?,

   @ColumnInfo(name = "NickName")
   var nickname:String?,

   @ColumnInfo(name = "Gender")
   var Gender:String?*/

/*
NOTE
 @ColumnInfo--> Column attribute its not complusory to declare.
@PrimaryKey--> it use to prohibited same primary key

*/
