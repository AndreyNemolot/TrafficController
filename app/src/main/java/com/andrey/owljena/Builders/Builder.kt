package com.andrey.owljena.Builders

import com.andrey.owljena.Models.Gadget
import com.andrey.owljena.Models.Road
import com.andrey.owljena.Models.Sign

interface Builder {

    fun setCar(id: Int, carName: String)
    fun setSign(sign: List<Sign>)
    fun setTrafficLight(id: Int, color: String)
    fun setDirection(id: Int, name: String)
    fun setRoadName(roadName: String)
    fun setGadget(gadget: List<Gadget>)
    fun getResult() : Road

}