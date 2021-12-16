package com.example.roomdatabase.database

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.roomdatabase.UserData

//https://android--code.blogspot.com/2018/07/android-kotlin-room-database-example.html
//https://medium.com/androiddevelopers/understanding-migrations-with-room-f01e04b07929 --important.
//https://medium.com/swlh/room-db-migrations-in-android-be9d5a045235
//https://android--code.blogspot.com/2019/07/android-kotlin-room-typeconverter.html
//https://www.youtube.com/watch?v=mNb58u1Gidw
//https://blog.mindorks.com/how-to-create-a-singleton-class-in-kotlin
//exportSchema - its is not complusry to declare
//@TypeConverters(Converter::class)
//auto migration

@Database(entities = [UserData::class], version = 3)
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
                       .fallbackToDestructiveMigration() // when upgrading versions, kill the original tables by using fallbackToDestructiveMigration()
                       //.fallbackToDestructiveMigration()//You can call this method to change this behavior to re-create the database instead of crashing. OR .fallbackToDestructiveMigrationFrom(*IntArray(68) { it + 1 }) // destroy everything before version 68 (included)
                       .addMigrations(Migration1To2,MIGRATION_2_3)
                       .build()

            }
            return instance!!
        }

        //Adding New Table
        private val Migration1To2 =object: Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE `Fruit` (`id` INTEGER, `name` TEXT, " +
                        "PRIMARY KEY(`id`))")
            }
        }
        //Adding new entity int
        val MIGRATION_2_3 = object: Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Fruit ADD COLUMN pub_year INTEGER")
            }
        }
        //Adding multiple new entity
        val Migration_3_4=object :Migration(3,4){
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE Fruit ADD COLUMN nickname ")
        }
        }
    }

}

//Adding New Table
private val Migration1To2 =object: Migration(1,2){
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE `Fruit` (`id` INTEGER, `name` TEXT, " +
                "PRIMARY KEY(`id`))")
    }
}
//Adding new entity int
val MIGRATION_2_3 = object: Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE Fruit ADD COLUMN pub_year INTEGER")
    }
}
//Adding new entity string
val Migration_3_4=object :Migration(3,4){//NOT NULL DEFAULT(deep)
override fun migrate(database: SupportSQLiteDatabase) {
    database.execSQL("ALTER TABLE Fruit ADD COLUMN nickname TEXT")
}
}
//Adding new entity string
val Migration_4_5=object :Migration(4,5){//NOT NULL DEFAULT(deep)
override fun migrate(database: SupportSQLiteDatabase) {
    database.execSQL("ALTER TABLE Fruit ADD COLUMN nickname1 TEXT NOT NULL")
}
}

/* //remove new entity string
 val Migration_5_6=object :Migration(5,6){//NOT NULL DEFAULT(deep)
 override fun migrate(database: SupportSQLiteDatabase) {
     database.execSQL("ALTER TABLE accounts ***DROP*** COLUMN password")

     database.execSQL("ALTER TABLE Fruit DROP COLUMN nickname1")
 }*//*
        }*/

//rename new entity string
val Migration_6_7=object :Migration(6,7){//NOT NULL DEFAULT(deep)
override fun migrate(database: SupportSQLiteDatabase) {
    database.execSQL("ALTER TABLE userdataT RENAME COLUMN name TO nickname2")
}
}
