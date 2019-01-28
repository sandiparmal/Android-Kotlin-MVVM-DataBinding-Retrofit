package infosys.com.kotlinmvvmsample.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import infosys.com.kotlinmvvmsample.service.model.Fact

/**
 * Interface which provide methods for accessing Database Access Objects
 */
@Database(entities = [(Fact::class)], version = 1)
abstract class FactDatabase : RoomDatabase() {
    /**
     * Return the Fact database object, using which application access CRUD
     * @return the Database Access Object
     */
    abstract fun factDao(): FactDao

    companion object {
        private var databaseName = "factDB.db"
        private var INSTANCE: FactDatabase? = null

        // Get database instance is not null else create new
        fun getDatabaseInstance(context: Context): FactDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: Room.databaseBuilder(context,
                            FactDatabase::class.java,
                            databaseName)
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build()
                }.also { INSTANCE = it }
    }
}