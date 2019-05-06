package com.andrey.owljena.Models

import java.io.Serializable

class Road : Serializable {

    var roadName: String
    var car: Car
    var sign: ArrayList<Sign>
    var trafficLight: TrafficLight

    init {
        this.roadName = ""
        this.car = Car()
        this.sign = ArrayList()
        this.trafficLight = TrafficLight()
    }
}