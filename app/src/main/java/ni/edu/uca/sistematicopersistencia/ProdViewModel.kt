package ni.edu.uca.sistematicopersistencia

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ni.edu.uca.sistematicopersistencia.data.database.entities.EntityProducto

class ProdViewModel(private val repository: ProdRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allWords: LiveData<List<EntityProducto>> = repository.allWords.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(word: EntityProducto) = viewModelScope.launch {
        repository.insert(word)
    }
}

class WordViewModelFactory(private val repository: ProdRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProdViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProdViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
