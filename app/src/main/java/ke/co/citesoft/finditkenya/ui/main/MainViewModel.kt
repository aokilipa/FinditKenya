package ke.co.citesoft.finditkenya.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    // Complete: Implement the ViewModel
    private val _data = MutableLiveData<String>()
    val data: LiveData<String>
        get() = _data
    init{
        _data.value = "Hello Jetpack!"
    }
}
