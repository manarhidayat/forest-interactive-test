package test.forest_interactive.presentation.di

import dagger.Component
import test.forest_interactive.domain.BreedsRepository
import test.forest_interactive.presentation.feature.breed.MainActivity
import test.forest_interactive.presentation.feature.breed.BreedsViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(breedsRepository: BreedsRepository)

    fun inject(viewModel: BreedsViewModel)

    fun inject(mainActivity: MainActivity)
}