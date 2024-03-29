package com.finalproject.tiketku.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finalproject.tiketku.model.onetrip.DataOneTrip
import com.finalproject.tiketku.model.onetrip.OneTripResponse
import com.finalproject.tiketku.model.roundtrip.DataRoundTrip
import com.finalproject.tiketku.model.roundtrip.RoundTripResponse
import com.finalproject.tiketku.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class JadwalViewModel @Inject constructor(private var api: ApiService, context: Context) : ViewModel() {

    private val liveDataRountrip: MutableLiveData<List<DataRoundTrip>?> = MutableLiveData()

    val livedataRountrip: MutableLiveData<List<DataRoundTrip>?> get() = liveDataRountrip

    private val jadwal: SharedPreferences = context.getSharedPreferences("date", Context.MODE_PRIVATE)
    private val startDatePref = jadwal.getString("startDate", "")
    private val returnDatePref = jadwal.getString("returnDate", "")


    private var startDatee = startDatePref
    private var returnDatee = returnDatePref

    fun getRountrip() {
        if (liveDataRountrip.value != null) {
            return
        }
        liveDataRountrip.value = null
        val tanggalBerangkat = startDatee
        val tanggalPulang = returnDatee
        if (tanggalBerangkat != null) {
            if (tanggalPulang != null) {
                api.getRoundtrip(tanggalBerangkat, tanggalPulang).enqueue(object : Callback<RoundTripResponse> {
                    override fun onResponse(
                        call: Call<RoundTripResponse>,
                        response: Response<RoundTripResponse>
                    ) {
                        if (response.isSuccessful) {
                            val rountripList = response.body()?.data
                            liveDataRountrip.postValue(rountripList)
                        } else {
                            liveDataRountrip.postValue(emptyList())
                        }
                    }

                    override fun onFailure(call: Call<RoundTripResponse>, t: Throwable) {
                        liveDataRountrip.postValue(emptyList())
                    }
                })
            }
        }
    }

    private val liveDataOnetrip: MutableLiveData<List<DataOneTrip>?> = MutableLiveData()

    val livedataOnetrip: MutableLiveData<List<DataOneTrip>?> get() = liveDataOnetrip
    fun getOnetrip() {
        if (liveDataOnetrip.value != null) {
            return
        }
        liveDataOnetrip.value = null
        val tanggalBerangkat = startDatee
        if (tanggalBerangkat != null) {
            api.getOnetrip(tanggalBerangkat).enqueue(object : Callback<OneTripResponse> {
                override fun onResponse(
                    call: Call<OneTripResponse>,
                    response: Response<OneTripResponse>
                ) {
                    if (response.isSuccessful) {
                        val rountripList = response.body()?.data
                        liveDataOnetrip.postValue(rountripList)
                    } else {
                        liveDataOnetrip.postValue(emptyList())
                    }
                }

                override fun onFailure(call: Call<OneTripResponse>, t: Throwable) {
                    liveDataOnetrip.postValue(emptyList())
                }
            })

        }
    }
}
