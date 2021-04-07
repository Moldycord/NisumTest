package com.example.nisumtest.viewmodel

import androidx.lifecycle.ViewModel
import com.example.nisumtest.models.ITunesResponse
import com.example.nisumtest.network.ServiceBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivityViewModel : ViewModel() {

    private val disposable = CompositeDisposable()

    fun searchSong(name: String) {
        disposable.add(
            ServiceBuilder.getService().searchSongs(name, 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    onITunesResponse(it)
                }, {
                    onError(it)
                })
        )
    }

    private fun onITunesResponse(iTunesResponse: ITunesResponse) {
        iTunesResponse.count
        iTunesResponse.resultList
    }

    private fun onError(error: Throwable) {
        error.printStackTrace()
    }
}