package com.andrey.owljena.Builders

import com.andrey.owljena.Models.Gadget
import com.andrey.owljena.Models.Road
import com.andrey.owljena.Models.Sign

class RoadBuilder:Builder {

    private val road = Road()


    override fun getResult(): Road {
        return road
    }

    override fun setGadget(gadget: List<Gadget>) {
        for (i in gadget.indices) {
            road.gadget.add(Gadget(gadget[i].id, gadget[i].name))
        }
    }

    override fun setCar(id: Int, carName: String) {
        road.car.id = id
        road.car.name = carName
    }

    override fun setSign(sign: List<Sign>) {
        for (i in sign.indices){
            road.sign.add(Sign(sign[i].id, sign[i].name))
        }

    }

    override fun setTrafficLight(id: Int, color: String) {
        road.trafficLight.id = id
        road.trafficLight.color = color
    }

    override fun setDirection(id: Int, name: String) {
        road.car.direction.id = id
        road.car.direction.name = name
    }

    override fun setRoadName(roadName: String) {
        road.roadName = roadName
    }
}