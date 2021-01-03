package test.forest_interactive.presentation.di

import dagger.Module
import dagger.Provides
import test.forest_interactive.data.api.CatApi
import test.forest_interactive.data.api.CatApiService
import test.forest_interactive.data.model.breed.BreedsItem
import test.forest_interactive.domain.BreedsRepository
import test.forest_interactive.presentation.feature.breed.BreedsAdapter
import test.forest_interactive.presentation.feature.breed.CustomDropDownAdapter
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideApi(): CatApi = CatApiService.getClient()

    @Provides
    fun provideBreedsRepository() =
        BreedsRepository()

    @Provides
    fun provideListData() = ArrayList<BreedsItem>()

    @Provides
    fun provideBreedsAdapter(data: ArrayList<BreedsItem>): BreedsAdapter =
        BreedsAdapter(data)

    @Provides
    fun provideCustomDropDownAdapter(): CustomDropDownAdapter =
        CustomDropDownAdapter()
}