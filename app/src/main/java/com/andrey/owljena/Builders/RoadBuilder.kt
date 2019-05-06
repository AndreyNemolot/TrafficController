package com.andrey.owljena.Builders

import com.andrey.owljena.Models.Gadget
import com.andrey.owljena.Models.Road
import com.andrey.owljena.Models.Sign

class RoadBuilder {

    private val road = Road()


    fun getResult(): Road {
        return road
    }

    fun setGadget(gadget: List<Gadget>) {
        for (i in gadget.indices) {
            road.car.gadget.add(Gadget(gadget[i].id, gadget[i].name))
        }
    }

    fun setCar(id: Int, carName: String) {
        road.car.id = id
        road.car.name = carName
    }

    fun setSign(sign: List<Sign>) {
        for (i in sign.indices) {
            road.sign.add(Sign(sign[i].id, sign[i].name))
        }

    }

    fun setTrafficLight(id: Int, color: String) {
        road.trafficLight.id = id
        road.trafficLight.color = color
    }

    fun setDirection(id: Int, name: String) {
        road.car.direction.id = id
        road.car.direction.name = name
    }

    fun setRoadName(roadName: String) {
        road.roadName = roadName
    }
}