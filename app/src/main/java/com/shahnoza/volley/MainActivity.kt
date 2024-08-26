package com.shahnoza.volley

import android.content.Context
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.Volley
import com.shahnoza.volley.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var requestQueue: RequestQueue
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestQueue= Volley.newRequestQueue(this) // volleyni tanitib olish




        if (isHaveNetwork()==true){
            binding.textView.text="Tarmoqqa ulangan"
        getImage(binding.myImageView,"https://img.freepik.com/free-vector/bird-colorful-logo-gradient-vector-343694-1365.jpg")
        }else{
            binding.textView.text="Tarmoqqa ulanmagan"

        }
        binding.btnCheck.setOnClickListener {

            if (isHaveNetwork()==true){
                binding.textView.text="Tarmoqqa ulangan"
                getImage(binding.myImageView,"https://img.freepik.com/free-vector/bird-colorful-logo-gradient-vector-343694-1365.jpg")
            }else{
                binding.textView.text="Tarmoqqa ulanmagan"
            }
        }


        // volley orqali internetdan surat olib kelish




    }

    fun isHaveNetwork():Boolean{
        val connectiveManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >=  Build.VERSION_CODES.M){ // api 23 dan yuqori bo`lgan qurilmalarga
            val activeNetwork = connectiveManager.activeNetwork // aktiv holatdagi tarmoq
            val networkCapabilities = connectiveManager.getNetworkCapabilities(activeNetwork) // tarmoq sifati
            return networkCapabilities != null && networkCapabilities.hasCapability(
                NetworkCapabilities.NET_CAPABILITY_INTERNET)// tarmoq sifati null bolmasa true
        }else{ // api 23 dan kichiklar uchun
            val activeNetworkInfo = connectiveManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }


    private fun getImage(imageView: ImageView,url:String){
        val imageRequest = ImageRequest(url,object : Response.Listener<Bitmap>{
            override fun onResponse(response: Bitmap?) { // Bitmap - rasmni saqlab olish uchun tip
                imageView.setImageBitmap(response)
            }
        },0, // rasm eni o`lchami
            0, // rasm bo`yi o`lchami
            ImageView.ScaleType.CENTER_CROP, // imageview ga o`rnashish xolati
            Bitmap.Config.ARGB_8888, // bitmap configi ranglar uchun
            object :Response.ErrorListener{
                override fun onErrorResponse(error: VolleyError?) {
                    Toast.makeText(this@MainActivity, "$error", Toast.LENGTH_SHORT).show()
                }
            })
        requestQueue.add(imageRequest)

    }


}
