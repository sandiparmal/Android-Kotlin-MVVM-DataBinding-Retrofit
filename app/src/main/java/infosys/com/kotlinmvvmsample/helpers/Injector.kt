package infosys.com.kotlinmvvmsample.helpers

import android.content.Context
import infosys.com.kotlinmvvmsample.database.DatabaseCache
import infosys.com.kotlinmvvmsample.database.FactDatabase


/**
 * Dependency Injector for DatabaseCache
 */
object Injector {

    fun provideCache(context: Context): DatabaseCache {
        val database: FactDatabase = FactDatabase.getDatabaseInstance(context)
        return DatabaseCache(database)
    }
}
