package ru.otus.weather

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import ru.otus.weather.api.Api
import ru.otus.weather.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels(factoryProducer = {

        object : AbstractSavedStateViewModelFactory(this, null) {
            override fun <T : ViewModel?> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T {
                val api = (application as App).retrofit.create(Api::class.java)
                return if (modelClass == MainViewModel::class.java) {
                    MainViewModel(api) as T
                } else {
                    throw ClassNotFoundException()
                }
            }
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.refresh.setOnRefreshListener {
            viewModel.onRefresh()
        }

        viewModel.data.observe(this, { weather ->
            with(binding) {
                refresh.isRefreshing = false

                cityName.text = weather.name
                temp.text = getString(R.string.temp, weather.main.temp)
                feelsTemp.text = getString(R.string.temp, weather.main.feelsLike)
                Picasso.get()
                    .load(String.format(ICON_URL, weather.weather[0].icon))
                    .into(image)
            }
        })

        viewModel.loading.observe(this, { binding.refresh.isRefreshing = it })
        viewModel.error.observe(this, {
            Snackbar.make(binding.root, getString(R.string.network_error), Snackbar.LENGTH_SHORT).show()
        })
    }
}