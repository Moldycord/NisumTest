package com.example.nisumtest.viewmodels

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.example.nisumtest.models.ITunesResponse
import com.example.nisumtest.network.repository.ITunesRepository
import com.example.nisumtest.views.SongItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivityViewModel : ViewModel() {

    private val iTunesRepository = ITunesRepository()

    private val _songList = MutableLiveData<List<SongItem>>()
    fun getSongList(): LiveData<List<SongItem>> = _songList
    private val disposable = CompositeDisposable()

    fun searchSong(name: String) {
        disposable.add(
            iTunesRepository.searchSong(name)
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
        _songList.postValue(iTunesResponse.resultList.map { SongItem(it) })
    }

    private fun onError(error: Throwable) {
        error.printStackTrace()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        disposable.dispose()
    }
}