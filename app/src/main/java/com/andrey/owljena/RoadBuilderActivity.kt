package com.andrey.owljena

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import ExpandableListViewAdapter
import android.content.Context
import com.andrey.owljena.Models.Category
import com.andrey.owljena.Models.RoadData
import kotlinx.android.synthetic.main.activity_road_builder.*
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.andrey.owljena.Builders.RoadBuilder
import com.andrey.owljena.Models.Road
import com.google.gson.Gson


class RoadBuilderActivity : AppCompatActivity() {

    var roadBuilderArray = ArrayList<Category>()
    val APP_PREFERENCES = "road"
    lateinit var roadName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_road_builder)
        setSupportActionBar(tbRoadBuilder)

        val intent = intent
        roadName = intent.getStringExtra("ROAD_NAME")
        roadBuilderArray = RoadData(this).roadBuildArray



        val adapter = ExpandableListViewAdapter(this, lvBuilder, roadBuilderArray)
        lvBuilder.setAdapter(adapter)

        lvBuilder.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
            selectItem(roadBuilderArray, groupPosition, childPosition)
            adapter.notifyDataSetChanged()
            true

        }

    }

    private fun createRoad(roadBuilderArray: ArrayList<Category>, roadName: String): Road {
        val roadBuilder = RoadBuilder()
        val roadParser = RoadParser(roadBuilder, roadBuilderArray, roadName)
        roadParser.parse()

        val road = roadBuilder.getResult()
        return road
    }

    private fun isRoadDone(road:Road):Boolean{
        return road.car.id!=-1 && !road.gadget.isEmpty() && !road.sign.isEmpty()
                && road.trafficLight.id!=-1
    }

    private fun saveRoad(road: Road) {
        val mPrefs = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        val prefsEditor = mPrefs.edit()
        val json = Gson().toJson(road)
        prefsEditor.putString(road.roadName, json)
        prefsEditor.apply()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.road_builder_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_save -> {
                val road = createRoad(roadBuilderArray, roadName)
                if(isRoadDone(road)){
                    saveRoad(road)
                    onBackPressed()
                }else{
                    Toast.makeText(this, "Some items didn't choise", Toast.LENGTH_SHORT).show()
                }

                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun selectItem(roadBuilderArray: ArrayList<Category>, groupPosition: Int, childPosition: Int) {
        if (roadBuilderArray[groupPosition].categoryName.equals("Car") ||
            roadBuilderArray.get(groupPosition).categoryName.equals("Traffic light") ||
            roadBuilderArray.get(groupPosition).categoryName.equals("Direction")
        ) {
            for (i in roadBuilderArray[groupPosition].subcategoryArray.indices) {
                roadBuilderArray[groupPosition].subcategoryArray.get(i).selected = false
            }
        }
        roadBuilderArray[groupPosition].subcategoryArray[childPosition].selected = !
        roadBuilderArray[groupPosition].subcategoryArray[childPosition].selected
    }

}
