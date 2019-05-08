package com.andrey.owljena.Builders

import com.andrey.owljena.Models.Crossroad
import com.andrey.owljena.Models.Road

class CrossroadBuilder {

    private val crossroad = Crossroad()

    fun setCrossroadType(crossroadType: String){
        crossroad.crossroadType = crossroadType
    }

    fun addRoad(road: Road){
        crossroad.roadMap.set(road.roadName, road)
    }


    fun getResult(): Crossroad {
        return crossroad
    }
}