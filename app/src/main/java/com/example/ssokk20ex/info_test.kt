package com.example.ssokk20ex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.okhttp.*
import kotlinx.android.synthetic.main.activity_info_test.*
import org.w3c.dom.Element
import java.io.IOException
import javax.xml.parsers.DocumentBuilderFactory

class info_test : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_test)

        btn.setOnClickListener {
            getInfo()
        }
    }

    private fun getInfo() {
        val request = getRequestUrl()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(response: Response) {
                var firestore  = FirebaseFirestore.getInstance()

                val body = response.body()?.string()?.byteInputStream()
                val buildFactory = DocumentBuilderFactory.newInstance()
                val docBuilder = buildFactory.newDocumentBuilder()
                val doc = docBuilder.parse(body, null)
                val nList = doc.getElementsByTagName("row")

                for (n in 0 until nList.length) {
                    val element = nList.item(n) as Element
                    val infoDTO = InfoDTO(
                        getValueFromKey(element, "GIGAN"),
                        getValueFromKey(element, "GUBUN_2"),
                        getValueFromKey(element, "JEONCHE"),
                        getValueFromKey(element, "NAMJA"),
                        getValueFromKey(element, "YEOJA")
                    )
                    val document = getValueFromKey(element, "nodeid")
                    firestore?.collection("Info")?.document(document)
                        ?.set(infoDTO)?.addOnCompleteListener { task ->
                            runOnUiThread {
                                if (task.isSuccessful) {
                                    text.text = "Insert Success"
                                } else {
                                    text.text = task.exception?.message
                                }
                            }
                        }
                }
            }

            override fun onFailure(request: Request, e: IOException) {
                val body = e.message
                runOnUiThread {
                    text.text = body
                }
            }
        })
    }

    private fun getRequestUrl() : Request {

        var url = "http://openapi.seoul.go.kr:8088/5368434a4f73686c3630647850416e/xml/octastatapi10676/1/5/"
        var httpUrl = HttpUrl.parse(url)
            ?.newBuilder()
            ?.build()

        return Request.Builder()
            .url(httpUrl)
//            .addHeader("Content-Type",
//                "application/x-www-form-urlencoded; text/xml; charset=utf-8")
            .build()
    }

    private fun getValueFromKey(element: Element, key: String) : String {
        return element.getElementsByTagName(key).item(0).firstChild.nodeValue
    }
}