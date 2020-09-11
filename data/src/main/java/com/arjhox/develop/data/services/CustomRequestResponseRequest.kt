package com.arjhox.develop.data.services

import com.android.volley.NetworkResponse
import com.android.volley.ParseError
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.JsonRequest
import com.arjhox.develop.data.models.RequestErrorResponse
import com.arjhox.develop.domain.models.RequestResponse
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset

open class CustomRequestResponseRequest(
    method: Int = Method.GET,
    url: String,
    jsonRequest: JSONObject?,
    listener: Response.Listener<RequestResponse>,
    errorListener: Response.ErrorListener
): JsonRequest<RequestResponse>(method, url, jsonRequest?.toString(), listener, errorListener) {

    private val htmlBeginner = "<!DOCTYPE"
    private val jsonArrayOpenChar = '['


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

    override fun parseNetworkError(volleyError: VolleyError?): VolleyError {
        return try {
            val requestErrorResponse = createRequestErrorResponse(volleyError)

            requestErrorResponse
        } catch (e: UnsupportedEncodingException) {
            ParseError(e)
        } catch (e: JSONException) {
            ParseError(e)
        }
    }


    private fun createRequestResponse(response: NetworkResponse?): RequestResponse {
        val jsonHeader = response?.headers.toString()
        val jsonResponse = String(
            response?.data ?: ByteArray(0),
            Charset.forName(HttpHeaderParser.parseCharset(response?.headers))
        )

        val jsonResponsePretty = when {
            jsonResponse.contains(htmlBeginner) -> jsonResponse
            jsonResponse[0] == jsonArrayOpenChar -> convertToPrettyString(JSONArray(jsonResponse))
            else -> convertToPrettyString(JSONObject(jsonResponse))
        }

        return RequestResponse(jsonHeader, jsonResponsePretty)
    }

    private fun createRequestErrorResponse(volleyError: VolleyError?): RequestErrorResponse {
        val statusCode = volleyError?.networkResponse?.statusCode
        val jsonHeader = volleyError?.networkResponse?.headers.toString()
        val jsonErrorResponse = String(
            volleyError?.networkResponse?.data ?: ByteArray(0),
            Charsets.UTF_8
        )

        return if (jsonErrorResponse.contains(htmlBeginner)) {
            RequestErrorResponse(statusCode, jsonHeader, jsonErrorResponse)
        } else {
            val jsonErrorResponsePretty = convertToPrettyString(JSONObject(jsonErrorResponse))

            RequestErrorResponse(statusCode, jsonHeader, jsonErrorResponsePretty)
        }
    }

    private fun <T>convertToPrettyString(jsonObject: T): String {
        val gSon = GsonBuilder().setPrettyPrinting().create()
        val jsonElement = JsonParser().parse(jsonObject.toString())

        return gSon.toJson(jsonElement)
    }

}