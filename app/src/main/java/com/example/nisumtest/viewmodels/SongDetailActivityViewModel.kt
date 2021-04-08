package com.example.nisumtest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nisumtest.models.ITunesResponse
import com.example.nisumtest.network.repository.ITunesRepository
import com.example.nisumtest.utils.ALBUM_WRAPPER
import com.example.nisumtest.views.SongItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SongDetailActivityViewModel : ViewModel() {

    private val iTunesRepository = ITunesRepository()

    private val _songList = MutableLiveData<List<SongItem>>()
    fun getSongList(): LiveData<List<SongItem>> = _songList
    private val disposable = CompositeDisposable()

    fun getSongsByArtist(albumId: Int) {
        disposable.add(
            iTunesRepository.getSongsByAlbumId(albumId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onITunesResponse(it) }, { onError(it) })
        )
    }

    private fun onITunesResponse(iTunesResponse: ITunesResponse) {
        val modifiedList = iTunesResponse.resultList.toMutableList()
        if (modifiedList[0].wrapperType == ALBUM_WRAPPER) {
            modifiedList.removeFirst()
        }
        _songList.postValue(modifiedList.map { SongItem(it) })
    }

    private fun onError(error: Throwable) {
        error.printStackTrace()
    }
}