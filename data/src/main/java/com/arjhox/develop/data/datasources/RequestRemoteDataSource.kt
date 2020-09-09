package com.arjhox.develop.data.datasources

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.arjhox.develop.data.services.CustomRequestResponseRequest
import com.arjhox.develop.data.services.VolleyRequestQueue
import com.arjhox.develop.domain.common.GET
import com.arjhox.develop.domain.models.RequestResponse
import io.reactivex.Single
import org.json.JSONObject
import com.arjhox.develop.data.models.Request as RequestData


interface RequestRemoteDataSource {

    fun playRequest(request: RequestData): Single<RequestResponse>

}


class RequestRemoteDataSourceImpl(
    private val context: Context
): RequestRemoteDataSource {

    override fun playRequest(request: RequestData): Single<RequestResponse> {
        return Single.create { emitter ->
            val jsonObjectRequest = object: CustomRequestResponseRequest(
                mapRequestType(request.requestType),
                concatParametersToPath(request),
                mapParametersToJsonObject(request.requestType, request.parameters),
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


    private fun mapRequestType(requestType: String): Int {
        return when(requestType) {
            GET -> Request.Method.GET
            else -> Request.Method.POST
        }
    }

    private fun concatParametersToPath(request: RequestData): String {
        return if (request.parameters.isEmpty() || request.requestType!=GET) {
            request.path
        } else {
            val stringBuilder = StringBuilder(request.path)
            stringBuilder.append("?")

            for (parameter in request.parameters) {
                stringBuilder.append(parameter.key).append("=").append(parameter.value).append("&")
            }

            val finalPath = stringBuilder.toString()

            finalPath.substring(0, finalPath.length-1)
        }
    }

    private fun mapParametersToJsonObject(requestType: String, parameters: Map<String, String>): JSONObject? {
        return if (requestType==GET || parameters.isEmpty()) {
            null
        } else {
            val jsonObject = JSONObject()

            for (parameter in parameters) {
                jsonObject.put(parameter.key, parameter.value)
            }

            jsonObject
        }
    }

}
