package test.forest_interactive.domain

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import test.forest_interactive.presentation.CatApplication
import test.forest_interactive.presentation.mappers.toDataEntityList
import test.forest_interactive.presentation.mappers.toDataList
import test.forest_interactive.data.api.CatApi
import test.forest_interactive.presentation.di.DaggerAppComponent
import test.forest_interactive.data.common.LIMIT
import test.forest_interactive.data.model.breed.BreedsItem
import javax.inject.Inject

class BreedsRepository {

    @Inject
    lateinit var catApiService: CatApi

    private val _data by lazy { MutableLiveData<List<BreedsItem>>() }
    val data: LiveData<List<BreedsItem>>
        get() = _data

    private val _isInProgress by lazy { MutableLiveData<Boolean>() }
    val isInProgress: LiveData<Boolean>
        get() = _isInProgress

    private val _isError by lazy { MutableLiveData<Boolean>() }
    val isError: LiveData<Boolean>
        get() = _isError


    init {
        DaggerAppComponent.create().inject(this)
    }

    fun fetchData(): Disposable {
        _isInProgress.postValue(true)

        getBreedsQuery();
        return catApiService.getBreeds(LIMIT)
            .subscribeOn(Schedulers.io())
            .subscribeWith(subscribeToDatabase())

    }

    private fun subscribeToDatabase(): DisposableSubscriber<List<BreedsItem>> {
        return object : DisposableSubscriber<List<BreedsItem>>() {

            override fun onNext(breedsResult: List<BreedsItem>?) {
                if (breedsResult != null) {
                    val entityList = breedsResult.toDataEntityList()
                    CatApplication.database.apply {
                        breedDao().insertData(entityList)
                    }
                }
            }

            override fun onError(t: Throwable?) {
                Log.e("insertData()", "BreedsResult error: ${t?.message}")
                _isError.postValue(true)
                _isInProgress.postValue(false)
            }

            override fun onComplete() {
                Log.v("insertData()", "insert success")
                getBreedsQuery()
                _isInProgress.postValue(false)

            }
        }
    }

    private fun getBreedsQuery(): Disposable {
        return CatApplication.database.breedDao()
            .queryData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { dataEntityList ->
                    if (dataEntityList != null && dataEntityList.isNotEmpty()) {
                        _isError.postValue(false)
                        _data.postValue(dataEntityList.toDataList())
                    }
                },
                {
                    Log.e("getBreedsQuery()", "Database error: ${it.message}")
                    _isError.postValue(true)
                    _isInProgress.postValue(false)
                }
            )
    }

}