package infosys.com.kotlinmvvmsample.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import infosys.com.kotlinmvvmsample.service.model.Fact

@Dao
interface FactDao {

    // insert facts in database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(facts: MutableList<Fact>)

    // get all facts from database
    @Query("SELECT * FROM fact")
    fun getAllFacts(): MutableList<Fact>

    //delete all facts from database
    @Query("DELETE FROM fact")
    fun deleteAllFacts()
}