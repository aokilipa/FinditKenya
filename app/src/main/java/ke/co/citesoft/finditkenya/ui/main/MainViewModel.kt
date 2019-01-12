package ke.co.citesoft.finditkenya.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    // Complete: Implement the ViewModel
    private val _data = MutableLiveData<String>()
    val data: LiveData<String>
        get() = _data
    init{
        _data.value = "Hello Jetpack!"
    }
}
