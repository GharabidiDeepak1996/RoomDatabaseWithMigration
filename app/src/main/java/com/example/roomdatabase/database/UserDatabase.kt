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
//auto migration
//https://blog.mindorks.com/room-database-with-kotlin-coroutines-in-android
//https://www.androidbugfix.com/2021/12/how-to-handle-query-method-return-type.html

@Database(entities = [UserData::class], version = 3)
/*@TypeConverters(Converter::class)*/ //--> https://www.tutorialspoint.com/sqlite/sqlite_data_types.htm
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDataDao(): UserDataDao


    companion object {
        @Volatile  //its will update to all decalred instance
        private var instance: UserDatabase? = null

        fun getInstance(ctx: Context): UserDatabase {
            if (instance == null) {
                 synchronized(this){ //its avoid the multiple creation of database instance through thread.
                     instance = Room.databaseBuilder(
                         ctx.applicationContext,
                         UserDatabase::class.java,
                         "user_data.db"
                     )

                         // when upgrading versions, kill the original tables by using fallbackToDestructiveMigration()
                         .fallbackToDestructiveMigration()//You can call this method to change this behavior to re-create the database instead of crashing. OR .fallbackToDestructiveMigrationFrom(*IntArray(68) { it + 1 }) // destroy everything before version 68 (included)
                         .addMigrations(Migration1To2,Migration_2_3)
                         .build()
                 }
            }
            return instance!!
        }

    /*    //Adding New Table
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
            database.execSQL("ALTER TABLE Fruit ADD COLUMN nickname")
            database.execSQL("ALTER TABLE Fruit ADD COLUMN nickname1")
        }
        }

        //update column name.
        val Migration_4_5=object :Migration(4,5){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Fruit RENAME  COLUMN nickname1 TO nickname2")
            }
        }

        //Change the table name
        val Migration_5_6=object :Migration(5,6){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Fruit RENAME TO Veg")
            }
        }

        //Drop the table name
        private val Migration_6_7=object :Migration(6,7){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("DROP TABLE Veg")
            }
        }

        //Add entity name into table existing data
        private val Migration_7_8=object :Migration(7,8){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE userdataT ADD COLUMN nickname TEXT DEFAULT ''")
            }
        }

        //Add entity name into table existing data
        private val Migration_8_9=object :Migration(8,9){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE userdataT ADD COLUMN nickname1 TEXT DEFAULT ''")
            }
        }
        private val Migration_9_10=object :Migration(9,10){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE userdataT ADD COLUMN nickname2 TEXT DEFAULT ''")
            }
        }*/


        //DROP doest work in sqlite or room database.
        //In SQLite, we cannot directly use the ALTER TABLE statement to drop a column in a table. Instead, we need to rename the table then create a new table,
        // and copy the data into the new table same as modifying table column type.
    //-----------------------Process of drop column-----------------------------------
        //Adding New Table
        private val Migration1To2 =object: Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE `userdata` (`id` INTEGER, `name` TEXT, " +
                        "PRIMARY KEY(`id`))")
            }
        }

        private val Migration_2_3=object :Migration(2,3){
            // copy all old data exception of drop column name to new table
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("INSERT INTO `userdata` (`id`, `name`) SELECT `id`, `name` FROM `userdataT`")
        }
    }
  }
}
