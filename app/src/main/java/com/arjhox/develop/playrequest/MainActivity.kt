package com.arjhox.develop.playrequest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.internal.GsonBuildConfig
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        textView = findViewById(R.id.textView)

        button.setOnClickListener { v: View? ->
            playRequest()
        }
    }



    private fun playRequest() {
        val url = "http://192.168.1.131:3000/usuario?desde=5&limite=2"
        val queue = Volley.newRequestQueue(this)
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
                Response.Listener {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val jsonElement = JsonParser().parse(it.toString())
                    textView.text = gson.toJson(jsonElement)
                },
                Response.ErrorListener {
                    textView.text = it.message
                }
        )

        queue.add(jsonObjectRequest)
    }
}