package com.arjhox.develop.data.datasources

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.arjhox.develop.data.services.VolleyRequestQueue
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import io.reactivex.Single
import org.json.JSONObject


interface RequestRemoteDataSource {

    fun playRequest(url: String): Single<String>

}


class RequestRemoteDataSourceImpl(
    private val context: Context
): RequestRemoteDataSource {

    override fun playRequest(url: String): Single<String> {
        return Single.create { emitter ->
            val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
                Response.Listener {
                    val prettyString = convertJsonToPrettyString(it)

                    emitter.onSuccess(prettyString)
                },
                Response.ErrorListener {
                    // TODO: Handle Error
                }
            )

            VolleyRequestQueue.getInstance(context).addToRequestQueue(jsonObjectRequest)
        }
    }


    private fun convertJsonToPrettyString(jsonObject: JSONObject): String {
        val gSon = GsonBuilder().setPrettyPrinting().create()
        val jsonElement = JsonParser().parse(jsonObject.toString())

        return gSon.toJson(jsonElement)
    }
}
