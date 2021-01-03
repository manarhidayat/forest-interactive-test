package test.forest_interactive.data.api

import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query
import test.forest_interactive.data.model.breed.BreedsItem

interface CatApi {

    @GET("v1/breeds")
    fun getBreeds(
        @Query("limit") rating: String
    ): Flowable<List<BreedsItem>>
}