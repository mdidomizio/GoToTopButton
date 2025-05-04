package com.example.gototopbutton

import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gototopbutton.databinding.ActivityMainBinding
import com.example.gototopbutton.model.Car
import com.example.gototopbutton.model.CarElement
import com.johncodeos.scrolltotopexample.Cars_RVAdapter
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    lateinit var carsCell: Car
    lateinit var adapter: Cars_RVAdapter
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "onCreate: Setting up RecyclerView")

        val recyclerView = binding.carsRv

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        binding.toolbarTitle.ellipsize = TextUtils.TruncateAt.MARQUEE
        binding.toolbarTitle.isSingleLine = true
        binding.toolbarTitle.isSelected = true
        binding.toolbarTitle.setOnClickListener { binding.carsRv.smoothScrollToPosition(0)}
        binding.toolbarTitle.text = "Scroll To Top Example"

        loadData()
    }

    private fun loadData() {
        Log.d(TAG, "loadData: Started loading data")
        carsCell = Car()
        try {
            val jsonLocation = loadJSONFromAsset()
            Log.d(TAG, "loadData: JSON loaded: ${jsonLocation?.take(100)}...")
            if (jsonLocation != null){
                val jsonArray = JSONArray(jsonLocation ?: "")
                Log.d(TAG, "loadData: JSON array length: ${jsonArray.length()}")
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.get(i) as JSONObject
                    val carMake = jsonObject.getString("car_make")
                    val carModel = jsonObject.getString("car_model")
                    val carModelYear = jsonObject.getInt("car_model_year")
                    carsCell.add(CarElement(carMake, carModel, carModelYear))
            }
                Log.d(TAG, "loadData: Loaded ${carsCell.size} car items")

                adapter = Cars_RVAdapter(carsCell)
                binding.carsRv.adapter = adapter

                // Verify adapter is set
                if (binding.carsRv.adapter == null) {
                    Log.e(TAG, "Adapter is null after setting")
                } else {
                    Log.d(TAG, "Adapter set successfully with ${adapter.itemCount} items")
                }

                // Notify if no items
                if (carsCell.size == 0) {
                    Toast.makeText(this, "No car data found", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Failed to load JSON data", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "loadData: JSON data is null")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun loadJSONFromAsset(): String? {
        val json : String?
        try {
            val `is` = this.assets.open("CarsDemoData.json")
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            json = String(buffer, Charset.forName("UTF-8"))
        } catch (ex: IOException) {
            Log.e(TAG, "loadJSONFromAsset: Error loading JSON file", ex)
            ex.printStackTrace()
            return null
        }
        return json
    }
}