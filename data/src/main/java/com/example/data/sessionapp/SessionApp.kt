package com.example.data.sessionapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

open class SessionApp {
    open val _sessionName: MutableLiveData<String?> = MutableLiveData<String?>(null)
    val sessionName: LiveData<String?> = _sessionName

}


class SessionAppMutable : SessionApp() {

    fun changeSessionName(newName: String) {
        super._sessionName.postValue(newName)
    }

    fun setSessionName(newName: String) {
        super._sessionName.postValue(newName)
    }
}