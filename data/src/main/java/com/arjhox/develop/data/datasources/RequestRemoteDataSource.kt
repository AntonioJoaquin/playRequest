package com.arjhox.develop.data.datasources

import android.content.Context
import com.android.volley.Response
import com.arjhox.develop.data.models.Request as RequestData
import com.arjhox.develop.data.services.CustomRequestResponseRequest
import com.arjhox.develop.data.services.VolleyRequestQueue
import com.arjhox.develop.domain.models.RequestResponse
import io.reactivex.Single


interface RequestRemoteDataSource {

    fun playRequest(request: RequestData): Single<RequestResponse>

}


class RequestRemoteDataSourceImpl(
    private val context: Context
): RequestRemoteDataSource {

    override fun playRequest(request: RequestData): Single<RequestResponse> {
        return Single.create { emitter ->
            val jsonObjectRequest = object: CustomRequestResponseRequest(Method.GET, request.path, null,
                Response.Listener {
                    emitter.onSuccess(it)
                },
                Response.ErrorListener {
                    emitter.onError(it)
                }
            ) {
                override fun getHeaders(): MutableMap<String, String> {
                    return request.headers.toMutableMap()
                }
            }

            VolleyRequestQueue.getInstance(context).addToRequestQueue(jsonObjectRequest)
        }
    }
}
