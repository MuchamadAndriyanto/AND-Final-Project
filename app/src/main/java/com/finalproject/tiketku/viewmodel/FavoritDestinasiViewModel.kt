import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finalproject.tiketku.model.favorit.DataFavorite
import com.finalproject.tiketku.model.favorit.ResponseFavoriteDestination
import com.finalproject.tiketku.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoritDestinasiViewModel : ViewModel() {

    private val liveDataFavorite: MutableLiveData<List<DataFavorite>?> = MutableLiveData()

    val livedataFavorite: MutableLiveData<List<DataFavorite>?> get() = liveDataFavorite

    fun getFavorite() {
        ApiClient.instance.getFavorite().enqueue(object : Callback<ResponseFavoriteDestination> {
            override fun onResponse(
                call: Call<ResponseFavoriteDestination>,
                response: Response<ResponseFavoriteDestination>
            ) {
                if (response.isSuccessful) {
                    val favoriteList = response.body()?.data
                    liveDataFavorite.postValue(favoriteList)
                } else {
                    liveDataFavorite.postValue(emptyList())
                }
            }

            override fun onFailure(call: Call<ResponseFavoriteDestination>, t: Throwable) {
                liveDataFavorite.postValue(emptyList())
            }
        })
    }
}
