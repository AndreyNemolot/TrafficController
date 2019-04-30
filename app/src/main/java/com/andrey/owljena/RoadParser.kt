package com.andrey.owljena

import com.andrey.owljena.Builders.RoadBuilder
import com.andrey.owljena.Models.Category
import com.andrey.owljena.Models.Gadget
import com.andrey.owljena.Models.Sign
import com.andrey.owljena.Models.SubCategory

class RoadParser(val roadBuilder: RoadBuilder, val roadBuilderArray: ArrayList<Category>, val roadName: String){

    val CAR = "Car"
    val CAR_GADGETS = "Car gadgets"
    val SIGN = "Sign"
    val TRAFFIC_LIGHT = "Traffic light"

    fun parse(){
        parseRoadName(roadName)
        for(i in roadBuilderArray.indices){
            getSubCategory(roadBuilderArray[i])
        }
    }

    private fun getSubCategory(category: Category) {
        when (category.categoryName) {
            CAR -> parseCar(category.subcategoryArray)
            CAR_GADGETS -> parseGadget(category.subcategoryArray)
            SIGN -> parseSign(category.subcategoryArray)
            TRAFFIC_LIGHT -> parseTrafficLight(category.subcategoryArray)
        }
    }

    fun parseCar(car: ArrayList<SubCategory>){
        for(i in car.indices){
            if(car[i].selected) {
                roadBuilder.setCar(i,car[i].subcategoryName)
            }
        }
    }

    fun parseGadget(gadget: ArrayList<SubCategory>){
        val gadgetResult = ArrayList<Gadget>()
        for(i in gadget.indices){
            if(gadget[i].selected) {
                gadgetResult.add(Gadget(i, gadget[i].subcategoryName))
            }
        }
        roadBuilder.setGadget(gadgetResult)

    }

    fun parseTrafficLight(trafficLight: ArrayList<SubCategory>){
        for(i in trafficLight.indices){
            if(trafficLight[i].selected) {
                roadBuilder.setTrafficLight(i, trafficLight[i].subcategoryName)
            }
        }
    }

    fun parseSign(sign: ArrayList<SubCategory>){
        val signResult = ArrayList<Sign>()
        for(i in sign.indices){
            if(sign[i].selected) {
                signResult.add(Sign(i, sign[i].subcategoryName))
            }
        }
        roadBuilder.setSign(signResult)
    }

    fun parseRoadName(roadName: String){
        roadBuilder.setRoadName(roadName)
    }

}