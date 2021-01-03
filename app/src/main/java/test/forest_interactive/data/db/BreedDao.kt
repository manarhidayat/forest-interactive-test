package test.forest_interactive.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single
import test.forest_interactive.data.entities.BreedEntity

@Dao
interface BreedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(data: List<BreedEntity>)

    @Query("SELECT * from breeds")
    fun queryData(): Single<List<BreedEntity>>
}