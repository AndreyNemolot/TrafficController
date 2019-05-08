package com.andrey.owljena.Models

import com.andrey.owljena.OntologyController

class ObjectAdapter {

    var controller: OntologyController

    init {
        controller = OntologyController()
    }

    fun createCrossroad(crossroad: Crossroad){
        controller.createIndividual("crossRoad", crossroad.crossroadType)
    }

    fun createCar(){}

    fun createSign(){}

    fun putRoadToCrossRoad(){}

    fun putCarToRoad(){}

    fun putSignToRoad(){}

    fun setCarNumber(){}

    fun setCarWay(){}


}