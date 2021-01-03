package test.forest_interactive.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import test.forest_interactive.data.entities.BreedEntity
import test.forest_interactive.data.common.DATABASE_NAME

@Database(entities = [BreedEntity::class], version = 1)
abstract class CatDatabase: RoomDatabase() {

    abstract fun breedDao(): BreedDao

    companion object {

        @Volatile // All threads have immediate access to this property
        private var instance: CatDatabase? = null

        private val LOCK = Any() // Makes sure no threads making the same thing at the same time

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                CatDatabase::class.java,
                DATABASE_NAME
            ).fallbackToDestructiveMigration().build()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
            instance
                ?: buildDatabase(
                    context
                )
                    .also { instance = it }
        }
    }
}