package com.andrey.owljena

import android.app.Dialog
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
import android.view.Gravity
import android.widget.LinearLayout
import android.content.DialogInterface
import android.graphics.Color
import android.support.v7.app.AlertDialog
import android.widget.TextView
import com.andrey.owljena.Builders.CrossroadBuilder


class CrossRoadActivity : AppCompatActivity(), OnClickableAreaClickedListener<Any> {

    //val roadMap = HashMap<String, Road>()
    var crossRoadType = "x_crossroad"
    val crossroadBuilder = CrossroadBuilder()

    override fun onResume() {
        super.onResume()
        getRoadsFromPref()
    }

    override fun onDestroy() {
        super.onDestroy()
        val mPrefs = getSharedPreferences(Constant.APP_PREFERENCES, Context.MODE_PRIVATE)
        mPrefs.edit().clear().apply()
    }

    fun getRoadsFromPref() {
        val mPrefs = getSharedPreferences(Constant.APP_PREFERENCES, Context.MODE_PRIVATE)
        for (i in Constant.ROAD.indices) {
            if (mPrefs.contains(Constant.ROAD[i])) {
                val json = mPrefs.getString(Constant.ROAD[i], "")
                val road = Gson().fromJson(json, Road::class.java)
                crossroadBuilder.getResult().roadMap[Constant.ROAD[i]] = road
                System.out.println("------------------")
                System.out.println("RoadName: " + road.roadName)
                System.out.println("Car: " + road.car)
                System.out.println("Gadget: " + road.car.gadget)
                System.out.println("TrafficLight: " + road.trafficLight)
                System.out.println("Sign: " + road.sign)
                System.out.println("------------------")

            }
            if (!crossroadBuilder.getResult().roadMap.isEmpty()) {
                //trafficLightSyncronizer(roadMap)
                val painter = Painter(this, crossroadBuilder.getResult())
                ivCrossroad.setImageBitmap(painter.paint())
            }

        }
    }
//    fun trafficLightSyncronizer(roadMap: HashMap<String, Road>){
//        roadMap[Constant.LEFT_ROAD]!!.trafficLight = roadMap[Constant.RIGHT_RAOD]!!.trafficLight
//        roadMap[Constant.UP_ROAD]!!.trafficLight = roadMap[Constant.DOWN_ROAD]!!.trafficLight
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cross_road)

        openChoiseCrossRoadDialog();

    }

    fun setCrossroad(type: String) {
        crossroadBuilder.setCrossroadType(type)
        val crossRoad = getBitmapFromAssets(type+".png")
        ivCrossroad.setImageBitmap(crossRoad)
        val photoViewer = PhotoViewAttacher(ivCrossroad)
        photoViewer.setScaleType(ImageView.ScaleType.FIT_XY)
        val clickableAreasImage = ClickableAreasImage(photoViewer, this)
        clickableAreasImage.clickableAreas = getClicableAreas()
    }


    private fun openChoiseCrossRoadDialog() {

        val alertDialog = AlertDialog.Builder(this).create()
        val appContext = this;
        // Set Custom Title
        val title = TextView(this)
        // Title Properties
        title.text = "Choice crossroad"
        title.setPadding(10, 10, 10, 10)
        title.gravity = Gravity.CENTER
        title.setTextColor(Color.BLACK)
        title.textSize = 20f
        alertDialog.setCustomTitle(title)


        // Set Button
        // you can more buttons
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "X-type cross road",
            DialogInterface.OnClickListener { dialog, which ->
                crossRoadType = "x_crossroad"
                setCrossroad(crossRoadType)
            })

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "T-type crossroad",
            DialogInterface.OnClickListener { dialog, which ->
                crossRoadType = "t_crossroad"
                setCrossroad(crossRoadType)
            })

        Dialog(applicationContext)
        alertDialog.show()

        // Set Properties for OK Button
        val xType = alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL)
        val neutralBtnLP = xType.getLayoutParams() as LinearLayout.LayoutParams
        neutralBtnLP.gravity = Gravity.FILL_VERTICAL
        xType.setPadding(50, 10, 10, 10)   // Set Position
        xType.setTextColor(Color.BLUE)
        xType.setLayoutParams(neutralBtnLP)

        val tType = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
        val negBtnLP = xType.getLayoutParams() as LinearLayout.LayoutParams
        negBtnLP.gravity = Gravity.FILL_VERTICAL
        tType.setTextColor(Color.RED)
        tType.setLayoutParams(negBtnLP)
    }


    private fun getClicableAreas(): ArrayList<ClickableArea<Any>> {
        val clickableAreas = ArrayList<ClickableArea<Any>>()
        clickableAreas.add(ClickableArea(0, 280, 180, 200, Constant.LEFT_ROAD))
        clickableAreas.add(ClickableArea(280, 280, 300, 200, Constant.RIGHT_RAOD))
        clickableAreas.add(ClickableArea(130, 450, 180, 280, Constant.DOWN_ROAD))
        if (crossroadBuilder.getResult().crossroadType == "x_crossroad") {
            clickableAreas.add(ClickableArea(130, 0, 180, 250, Constant.UP_ROAD))

        }
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
