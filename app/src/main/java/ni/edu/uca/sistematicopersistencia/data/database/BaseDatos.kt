package ni.edu.uca.sistematicopersistencia.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ni.edu.uca.sistematicopersistencia.data.database.dao.ProductoDao
import ni.edu.uca.sistematicopersistencia.data.database.entities.EntityProducto

@Database(entities = [EntityProducto::class], version =1, exportSchema = false )
abstract class BaseDatos: RoomDatabase(){
    abstract fun productoDao(): ProductoDao

    companion object{
        @Volatile
        private var INSTANCE: BaseDatos?= null

        fun obtBaseDatos(context: Context):BaseDatos{
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BaseDatos::class.java,
                    "basedatos"
                ).build()
                INSTANCE = instance
                instance
            }
        }

        private class ProdDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            /**
             * Override the onCreate method to populate the database.
             */
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.productoDao())
                    }
                }
            }
        }

        /**
         * Populate the database in a new coroutine.
         * If you want to start with more words, just add them.
         */
        suspend fun populateDatabase(prodDao: ProductoDao) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            prodDao.deleteAll()

            var word = EntityProducto(1, "Coca", 25.0, 1)
            prodDao.insertarReg(word)
                word = EntityProducto(2, "Pepsi", 20.0, 1)
            prodDao.insertarReg(word)
        }
    }
}