package ru.otus.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.otus.weather.api.Api
import ru.otus.weather.api.dto.CityWeatherResponse

class MainViewModel(private val api: Api) : ViewModel() {

    private val mData = MutableLiveData<CityWeatherResponse>()
    private val mLoading = MutableLiveData<Boolean>()
    private val mError: MutableLiveData<Unit> = SingleLiveEvent()

    val data: LiveData<CityWeatherResponse> = mData
    val loading: LiveData<Boolean> = mLoading
    val error: LiveData<Unit> = mError

    init {
        load()
    }

    fun onRefresh() {
        load()
    }

    private fun load() {
        viewModelScope.launch {
            try {
                mLoading.value = true
                mData.value = api.getWeather(CITY_ID, Units.METRIC)
            } catch (error: Throwable) {
                error.printStackTrace()
                mError.value = Unit
            } finally {
                mLoading.value = false
            }
        }
    }
}