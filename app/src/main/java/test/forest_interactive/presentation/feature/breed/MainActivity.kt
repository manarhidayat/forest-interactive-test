package test.forest_interactive.presentation.feature.breed

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import test.forest_interactive.R
import test.forest_interactive.databinding.ActivityMainBinding
import test.forest_interactive.presentation.di.DaggerAppComponent
import test.forest_interactive.data.model.breed.BreedsItem
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var breedsAdapter: BreedsAdapter

    @Inject
    lateinit var spinnerAdapter: CustomDropDownAdapter

    lateinit var binding: ActivityMainBinding

    private val viewModel: BreedsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        DaggerAppComponent.create().inject(this)
        setUpSpinner()
        observeLiveData()
    }

    private fun setUpSpinner() {
        spinnerAdapter = CustomDropDownAdapter()
        spinner.adapter = spinnerAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (parent != null) {
                    Log.e("Breed: ", parent.getItemAtPosition(position).toString())
                    binding.breed = parent.getItemAtPosition(position) as BreedsItem?

                    val animation: Animation =  AnimationUtils.loadAnimation(
                        applicationContext,
                        R.anim.up_down
                    )
                    content.startAnimation(animation)
                };
            }

        }
    }


    private fun observeLiveData() {
        observeInProgress()
        observeIsError()
        observeBreedList()
    }

    private fun observeInProgress() {
        viewModel.repository.isInProgress.observe(this, Observer { isLoading ->
            isLoading.let {
                if (it) {
                    empty_text.visibility = View.GONE
                    fetch_progress.visibility = View.VISIBLE
                } else {
                    fetch_progress.visibility = View.GONE
                }
            }
        })
    }

    private fun observeIsError() {
        viewModel.repository.isError.observe(this, Observer { isError ->
            isError.let {
                if (it) {
                    disableViewsOnError()
                } else {
                    empty_text.visibility = View.GONE
                }
            }
        })
    }

    private fun disableViewsOnError() {
        empty_text.visibility = View.VISIBLE
        breedsAdapter.setUpData(emptyList())
        fetch_progress.visibility = View.GONE
    }

    private fun observeBreedList() {
        viewModel.repository.data.observe(this, Observer { breeds ->
            breeds.let {
                if (it != null && it.isNotEmpty()) {
                    Log.e("wew", it.toString())
                    spinnerAdapter.setUpData(it)

                } else {
                    disableViewsOnError()
                }
            }
        })
    }
}
