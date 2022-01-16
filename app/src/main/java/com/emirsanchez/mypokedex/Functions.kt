package com.emirsanchez.mypokedex

object Functions {

    fun getUrlNumber(url:String):Int
    {
        val splitUrl = url.split("/")
        return splitUrl[splitUrl.lastIndex-1].toInt()
    }

}