package com.example.roomdatabase.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.roomdatabase.UserData

//https://android--code.blogspot.com/2018/07/android-kotlin-room-database-example.html
//https://medium.com/androiddevelopers/understanding-migrations-with-room-f01e04b07929 --important.
//https://medium.com/swlh/room-db-migrations-in-android-be9d5a045235
//https://android--code.blogspot.com/2019/07/android-kotlin-room-typeconverter.html
//https://www.youtube.com/watch?v=mNb58u1Gidw
//exportSchema - its is not complusry to declare

@Database(entities = [UserData::class], version = 1)
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDataDao(): UserDataDao


    companion object {
        @Volatile
        private var instance: UserDatabase? = null

        fun getInstance(ctx: Context): UserDatabase {
            if (instance == null) {

                   instance = Room.databaseBuilder(
                       ctx.applicationContext,
                       UserDatabase::class.java,
                       "user_data.db"
                   )
                       //.fallbackToDestructiveMigration()//You can call this method to change this behavior to re-create the database instead of crashing.
                      // .addMigrations(Migration1To2)
                       .build()

            }
            return instance!!
        }
        //this for adding other colum
        private val Migration1To2 =object: Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE userdataT ADD COLUMN NickName TEXT NOT NULL DEFAULT(deep)")
            }
        }
    }

}
