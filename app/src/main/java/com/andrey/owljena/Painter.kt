package com.andrey.owljena

import android.content.Context
import android.graphics.*
import com.andrey.owljena.Models.Road
import android.graphics.Bitmap
import kotlin.math.sign


class Painter(val context: Context, val roadMap: HashMap<String, Road>) {

    val LEFT_ROAD = "LeftRoad"
    val RIGHT_RAOD = "RightRoad"
    val UP_ROAD = "UpRoad"
    val DOWN_ROAD = "DownRoad"
    val CAR_COORDINATE: HashMap<String, Array<Int>> = hashMapOf(
        LEFT_ROAD to arrayOf(20, 900), RIGHT_RAOD to arrayOf(850, 750),
        UP_ROAD to arrayOf(350, 450), DOWN_ROAD to arrayOf(550, 1250)
    )

    val TRAFFIC_LIGHT_COORDINATE: HashMap<String, Array<Int>> = hashMapOf(
        LEFT_ROAD to arrayOf(120, 1130), RIGHT_RAOD to arrayOf(850, 700),
        UP_ROAD to arrayOf(250, 550), DOWN_ROAD to arrayOf(750, 1250)
    )
    val SIGN_COORDINATE: HashMap<String, Array<Int>> = hashMapOf(
        LEFT_ROAD to arrayOf(100, 1230, 100, 1330), RIGHT_RAOD to arrayOf(850, 600, 850, 500),
        UP_ROAD to arrayOf(150, 550, 50, 550), DOWN_ROAD to arrayOf(850, 1250, 950, 1250)
    )
    val GADGET_COORDINATE: HashMap<String, Array<Int>> = hashMapOf(
        LEFT_ROAD to arrayOf(220, 1130, 220, 1030), RIGHT_RAOD to arrayOf(750, 700,750, 800),
        UP_ROAD to arrayOf(250, 650, 350, 650), DOWN_ROAD to arrayOf(550, 1150, 450, 1150)
    )


    fun paint(): Bitmap {
        var crossroadBitmap = getBitmapFromAssets("x_crossroad.png")

        for ((key, value) in roadMap) {

            when (key) {
                LEFT_ROAD -> {
                    crossroadBitmap = paintRoad(value, crossroadBitmap, 90)
                }
                RIGHT_RAOD -> {
                    crossroadBitmap = paintRoad(value, crossroadBitmap, 270)

                }
                UP_ROAD -> {
                    crossroadBitmap = paintRoad(value, crossroadBitmap, 180)

                }
                DOWN_ROAD -> {
                    crossroadBitmap = paintRoad(value, crossroadBitmap, 0)

                }
            }

        }
        return crossroadBitmap
    }

    private fun paintRoad(road: Road, crossroadBitmap: Bitmap, degree: Int): Bitmap {


        var result = Bitmap.createBitmap(crossroadBitmap.width, crossroadBitmap.height, Bitmap.Config.ARGB_4444)
        val canvas = Canvas(result)

        val paint = Paint()
        canvas.drawBitmap(crossroadBitmap, (0).toFloat(), (0).toFloat(), paint)


        result = paintCar(canvas, result, road, degree)

        result = paintTrafficLight(canvas, result, road)

        for (i in road.sign.indices){
            result = paintSign(canvas, result, road, road.sign[i].id)
        }
        for (i in road.gadget.indices) {
            result = paintGadget(canvas, result, road, road.gadget[i].id)

        }





        return result
    }

    private fun paintCar(canvas: Canvas, result: Bitmap, road: Road, degree: Int): Bitmap {
        val roadName = road.roadName

        val paint = Paint()
        val carBitmap = rotateBitmap(getBitmapFromAssets(road.car.id.toString() + "_car.png"), degree)
        canvas.drawBitmap(
            carBitmap,
            CAR_COORDINATE[roadName]!![0].toFloat(),
            CAR_COORDINATE[roadName]!![1].toFloat(),
            paint
        )

        return result
    }

    private fun paintTrafficLight(canvas: Canvas, result: Bitmap, road: Road): Bitmap {
        if (road.trafficLight.id != 3) {
            val roadName = road.roadName

            val paint = Paint()
            val trafficLightBitmap = getBitmapFromAssets(road.trafficLight.id.toString() + "_traffic_light.png")
            canvas.drawBitmap(
                trafficLightBitmap,
                (TRAFFIC_LIGHT_COORDINATE[roadName]!![0]).toFloat(),
                TRAFFIC_LIGHT_COORDINATE[roadName]!![1].toFloat(),
                paint
            )
        }

        return result
    }

    private fun paintSign(canvas: Canvas, result: Bitmap, road: Road, id: Int): Bitmap {
        val roadName = road.roadName

        val paint = Paint()
        val trafficLightBitmap = getBitmapFromAssets(id.toString() + "_sign.png")
        var x = 0
        if(id!=0){
            x=id*2
        }
        canvas.drawBitmap(
            trafficLightBitmap,
            (SIGN_COORDINATE[roadName]!![0+x]).toFloat(),
            SIGN_COORDINATE[roadName]!![1+x].toFloat(),
            paint
        )

        return result
    }

    private fun paintGadget(canvas: Canvas, result: Bitmap, road: Road, id: Int): Bitmap {
        val roadName = road.roadName

        val paint = Paint()
        val trafficLightBitmap = getBitmapFromAssets(id.toString() + "_gadget.png")
        var x = 0
        if(id!=0){
            x=id*2
        }
        canvas.drawBitmap(
            trafficLightBitmap,
            (GADGET_COORDINATE[roadName]!![0+x]).toFloat(),
            GADGET_COORDINATE[roadName]!![1+x].toFloat(),
            paint
        )

        return result
    }


    private fun rotateBitmap(item: Bitmap, degree: Int): Bitmap {
        val matrix = Matrix()
        matrix.postRotate((degree).toFloat())

        return Bitmap.createBitmap(item, 0, 0, item.width, item.height, matrix, true)
    }


    fun getBitmapFromAssets(fileName: String): Bitmap {
//        if (fileName != "") {
        val assetManager = context.assets
        val istr = assetManager.open(fileName)
        val bitmap = BitmapFactory.decodeStream(istr)
        istr.close()
        return bitmap
//        }
//        return null
    }
}