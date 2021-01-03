package test.forest_interactive.presentation.feature.breed

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import test.forest_interactive.presentation.di.DaggerAppComponent
import test.forest_interactive.domain.BreedsRepository
import javax.inject.Inject

class BreedsViewModel: ViewModel() {
    @Inject
    lateinit var repository: BreedsRepository

    private val compositeDisposable by lazy { CompositeDisposable() }

    init {
        DaggerAppComponent.create().inject(this)
        compositeDisposable.add(repository.fetchData())
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}