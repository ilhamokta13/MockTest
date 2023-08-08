package com.ilham.mocktest.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ilham.mocktest.local.DatabaseToko
import com.ilham.mocktest.model.DataToko
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeViewModel (app : Application) : AndroidViewModel(app) {

    // Implement the ViewModel
    var mToko : MutableLiveData<List<DataToko>>

    init {
        mToko = MutableLiveData()
        getToko()
    }

    //LiveData Observer
    fun getNoteObservers(): MutableLiveData<List<DataToko>> {
        return mToko
    }

    //mengambil data untuk di tampilkan
    fun getToko() {
        GlobalScope.launch {
            val dataDao = DatabaseToko.getInstance(getApplication())!!.tokoDao()
            val listNote = dataDao.getTokoDESC()
            mToko.postValue(listNote)
        }
    }

    fun insert(dataToko: DataToko){
        val dataDao = DatabaseToko.getInstance(getApplication())?.tokoDao()
        dataDao!!.insertToko(dataToko)
        getToko()
    }

    fun delete(dataToko: DataToko){
        val dataDao = DatabaseToko.getInstance(getApplication())!!.tokoDao()
        dataDao?.deleteToko(dataToko)
        getToko()
    }

    fun update(dataToko: DataToko){
        val dataDao = DatabaseToko.getInstance(getApplication())!!.tokoDao()
        dataDao?.updateToko(dataToko)
        getToko()
    }

}