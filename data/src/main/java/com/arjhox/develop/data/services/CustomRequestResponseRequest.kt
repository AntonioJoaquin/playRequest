package com.arjhox.develop.data.services

import com.android.volley.NetworkResponse
import com.android.volley.ParseError
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.JsonRequest
import com.arjhox.develop.domain.models.RequestResponse
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset

class CustomRequestResponseRequest(
    method: Int = Method.GET,
    url: String,
    jsonRequest: JSONObject?,
    listener: Response.Listener<RequestResponse>,
    errorListener: Response.ErrorListener
): JsonRequest<RequestResponse>(method, url, jsonRequest?.toString(), listener, errorListener) {

    override fun parseNetworkResponse(response: NetworkResponse?): Response<RequestResponse> {
        return try {
            val requestResponse = createRequestResponse(response)

            Response.success(requestResponse, HttpHeaderParser.parseCacheHeaders(response))
        } catch (e: UnsupportedEncodingException) {
            Response.error(ParseError(e))
        } catch (e: JSONException) {
            Response.error(ParseError(e))
        }
    }


    private fun createRequestResponse(response: NetworkResponse?): RequestResponse {
        val jsonHeader = response?.headers.toString()
        val jsonResponse = String(
            response?.data ?: ByteArray(0),
            Charset.forName(HttpHeaderParser.parseCharset(response?.headers))
        )

        val jsonResponsePretty = if (jsonResponse[0] == '[') {
            convertToPrettyString(JSONArray(jsonResponse))
        } else {
            convertToPrettyString(JSONObject(jsonResponse))
        }

        return RequestResponse(jsonHeader, jsonResponsePretty)
    }

    private fun <T>convertToPrettyString(jsonObject: T): String {
        val gSon = GsonBuilder().setPrettyPrinting().create()
        val jsonElement = JsonParser().parse(jsonObject.toString())

        return gSon.toJson(jsonElement)
    }

}