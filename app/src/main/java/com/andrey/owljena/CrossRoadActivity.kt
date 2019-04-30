package com.andrey.owljena

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_cross_road.*
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.widget.ImageView
import at.lukle.clickableareasimage.ClickableAreasImage
import at.lukle.clickableareasimage.OnClickableAreaClickedListener
import uk.co.senab.photoview.PhotoViewAttacher
import at.lukle.clickableareasimage.ClickableArea
import java.util.*
import com.google.gson.Gson
import com.andrey.owljena.Models.Road
import kotlin.collections.HashMap

class CrossRoadActivity : AppCompatActivity(), OnClickableAreaClickedListener<Any> {

    val LEFT_ROAD = "LeftRoad"
    val RIGHT_RAOD = "RightRoad"
    val UP_ROAD = "UpRoad"
    val DOWN_ROAD = "DownRoad"
    val APP_PREFERENCES = "road"
    val ROAD = arrayOf(LEFT_ROAD, RIGHT_RAOD, UP_ROAD, DOWN_ROAD)
    val roadMap = HashMap<String, Road>()

    override fun onResume() {
        super.onResume()
        getRoadsFromPref()
    }

    override fun onDestroy() {
        super.onDestroy()
        val mPrefs = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        mPrefs.edit().clear().apply()
    }

    fun getRoadsFromPref(){
        val mPrefs = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        for(i in ROAD.indices){
            if (mPrefs.contains(ROAD[i])) {
                val json = mPrefs.getString(ROAD[i], "")
                val road = Gson().fromJson(json, Road::class.java)
                roadMap[ROAD[i]] = road
                System.out.println("------------------")
                System.out.println("RoadName: " + road.roadName)
                System.out.println("Car: " + road.car)
                System.out.println("Gadget: " + road.gadget)
                System.out.println("TrafficLight: " + road.trafficLight)
                System.out.println("Sign: " + road.sign)
                System.out.println("------------------")

            }
            if (!roadMap.isEmpty()){
                //trafficLightSyncronizer(roadMap)
                val painter = Painter(this, roadMap)
                ivCrossroad.setImageBitmap(painter.paint())
            }

        }
    }
    fun trafficLightSyncronizer(roadMap: HashMap<String, Road>){
        roadMap[LEFT_ROAD]!!.trafficLight = roadMap[RIGHT_RAOD]!!.trafficLight
        roadMap[UP_ROAD]!!.trafficLight = roadMap[DOWN_ROAD]!!.trafficLight

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cross_road)
        val crossRoad = getBitmapFromAssets("x_crossroad.png")
        ivCrossroad.setImageBitmap(crossRoad)

        val photoViewer = PhotoViewAttacher(ivCrossroad)
        photoViewer.setScaleType(ImageView.ScaleType.FIT_XY)
        val clickableAreasImage = ClickableAreasImage(photoViewer, this)
        clickableAreasImage.clickableAreas = getClicableAreas()

    }


    private fun getClicableAreas(): ArrayList<ClickableArea<Any>> {
        val clickableAreas = ArrayList<ClickableArea<Any>>()
        clickableAreas.add(ClickableArea(0, 280, 180, 200, LEFT_ROAD))
        clickableAreas.add(ClickableArea(280, 280, 300, 200, RIGHT_RAOD))
        clickableAreas.add(ClickableArea(130, 0, 180, 250, UP_ROAD))
        clickableAreas.add(ClickableArea(130, 450, 180, 280, DOWN_ROAD))
        return clickableAreas
    }

    private fun getBitmapFromAssets(fileName: String): Bitmap {
        val assetManager = assets
        val istr = assetManager.open(fileName)
        val bitmap = BitmapFactory.decodeStream(istr)
        istr.close()
        return bitmap
    }

    override fun onClickableAreaTouched(item: Any) {
        if (item is String) {
            val roadName = item.toString()
            val intent = Intent(this, RoadBuilderActivity::class.java)
            intent.putExtra("ROAD_NAME", roadName);
            startActivity(intent)

        }
    }
}
