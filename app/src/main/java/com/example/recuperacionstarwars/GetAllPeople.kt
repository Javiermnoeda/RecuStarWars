package com.example.recuperacionstarwars
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.lang.Exception
import java.text.ParseException

class GetAllPeople {
    companion object{
        suspend fun send(): Unit = withContext(Dispatchers.IO){
            val client = OkHttpClient()
            val url= "https://swapi.dev/api/people/"
            val request = Request.Builder()
                    .url(url)
                    .build()

            val call = client.newCall(request)
            try{
                val response = call.execute()
                val bodyInString = response.body?.string()
                bodyInString?.let {
                    val JsonObject = JSONObject(bodyInString)

                    val results = JsonObject.optJSONArray("results")

                    results?.let{
                        val gson = Gson()

                        val itemType = object : TypeToken<List<People>>() {}.type

                        return@withContext gson.fromJson(results.toString(), itemType)
                    }
                }

            } catch (ex : Exception){
                Log.e("TAG", "Algo fue mal")

            }
        }
    }

}