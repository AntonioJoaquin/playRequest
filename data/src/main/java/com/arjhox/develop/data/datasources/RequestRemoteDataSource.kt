package com.arjhox.develop.data.datasources

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.arjhox.develop.data.services.CustomRequestResponseRequest
import com.arjhox.develop.data.services.VolleyRequestQueue
import com.arjhox.develop.domain.models.RequestResponse
import io.reactivex.Single


interface RequestRemoteDataSource {

    fun playRequest(url: String): Single<RequestResponse>

}


class RequestRemoteDataSourceImpl(
    private val context: Context
): RequestRemoteDataSource {

    override fun playRequest(url: String): Single<RequestResponse> {
        return Single.create { emitter ->
            val jsonObjectRequest = CustomRequestResponseRequest(Request.Method.GET, url, null,
                Response.Listener {
                    emitter.onSuccess(it)
                },
                Response.ErrorListener {
                    emitter.onError(it)
                }
            )

            VolleyRequestQueue.getInstance(context).addToRequestQueue(jsonObjectRequest)
        }
    }
}
