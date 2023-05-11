package ni.edu.uca.sistematicopersistencia

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import ni.edu.uca.sistematicopersistencia.data.database.dao.ProductoDao
import ni.edu.uca.sistematicopersistencia.data.database.entities.EntityProducto

class ProdRepository(private val productoDao: ProductoDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allWords: Flow<List<EntityProducto>> = productoDao.obtRegistos()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(word: EntityProducto) {
        productoDao.insertarReg(word)
    }
}