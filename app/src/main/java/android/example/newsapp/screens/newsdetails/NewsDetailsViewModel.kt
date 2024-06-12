package android.example.newsapp.screens.newsdetails

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class NewsDetailsViewModel(private val application: Application) : AndroidViewModel(application) {

    val title = MutableLiveData<String>()
    val content = MutableLiveData<String>()
    val imageUrl = MutableLiveData<String>()


    private val _isShareClicked = MutableLiveData<Boolean>()
    val isShareClicked: MutableLiveData<Boolean>
        get() = _isShareClicked

    private val _isLinkClicked = MutableLiveData<Boolean>()
    val isLinkClicked: MutableLiveData<Boolean>
        get() = _isLinkClicked

    private val _isImageClicked = MutableLiveData<Boolean>()
    val isImageClicked: MutableLiveData<Boolean>
        get() = _isImageClicked

    fun onShareClicked() {
        _isShareClicked.value = true
    }

    fun onLinkClicked() {
        _isLinkClicked.value = true
    }

    fun onImageClicked() {
        _isImageClicked.value = true
    }

    fun onShareComplete() {
        _isShareClicked.value = false
    }

    fun onLinkComplete() {
        _isLinkClicked.value = false
    }

    fun onImageClickComplete() {
        _isImageClicked.value = false
    }
}