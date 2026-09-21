package com.toletspot.houseforrent

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class sharedHelper {
    var sharedPreferences: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null

    fun putString(context: Context, Key: String?, Value: String?) {
        try {
            sharedPreferences = context.getSharedPreferences("Cache", Context.MODE_PRIVATE)
            editor = sharedPreferences!!.edit()
            editor?.putString(Key, Value)
            editor?.commit()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    fun getString(contextGetKey: Context, Key: String?): String? {
        sharedPreferences = contextGetKey.getSharedPreferences("Cache", Context.MODE_PRIVATE)
        return sharedPreferences!!.getString(Key, "")
    }

    fun putLong(context: Context, Key: String?, Value: Long?) {
        try {
            sharedPreferences = context.getSharedPreferences("Cache", Context.MODE_PRIVATE)
            editor = sharedPreferences!!.edit()
            Value?.let { editor?.putLong(Key, it) }
            editor?.commit()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    fun getLong(contextGetKey: Context, Key: String?): Long {
        sharedPreferences = contextGetKey.getSharedPreferences("Cache", Context.MODE_PRIVATE)
        return sharedPreferences!!.getLong(Key,0L )
    }

    fun putInt(context: Context, Key: String?, Value: Int?) {
        try {
            sharedPreferences = context.getSharedPreferences("Cache", Context.MODE_PRIVATE)
            editor = sharedPreferences!!.edit()
            Value?.let { editor?.putInt(Key, it) }
            editor?.commit()
        }
        catch (e:Exception){
            e.printStackTrace()
        }
    }

    fun getInt(contextGetKey: Context, Key: String?): Int {
        sharedPreferences = contextGetKey.getSharedPreferences("Cache", Context.MODE_PRIVATE)
        return sharedPreferences!!.getInt(Key,0)
    }

    fun putBoolean(context: Context, Key: String?, Value: Boolean?) {
        try {
            sharedPreferences = context.getSharedPreferences("Cache", Context.MODE_PRIVATE)
            editor = sharedPreferences!!.edit()
            editor?.putBoolean(Key, Value!!)
            editor?.commit()
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getBoolean(contextGetKey: Context, Key: String): Boolean {
        sharedPreferences = contextGetKey.getSharedPreferences("Cache", Context.MODE_PRIVATE)
        return sharedPreferences!!.getBoolean(Key, false)
    }

    fun setLists(list:ArrayList<String>, context: Context,arrayName : String){
        sharedPreferences = context.getSharedPreferences("Cache", Context.MODE_PRIVATE)
        editor = sharedPreferences!!.edit()
        val gson = Gson()
        val json = gson.toJson(list)
        editor!!.putString(arrayName,json)
        editor!!.commit()
    }

    fun getList(context: Context,arrayName : String):ArrayList<String>{
        sharedPreferences = context.getSharedPreferences("Cache", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences!!.getString(arrayName,null)
        val type = object : TypeToken<ArrayList<String>>(){}.type
        return gson.fromJson(json,type)
    }

    fun putInt_noti(context: Context, Key: String?, Value: Int?) {
        try {
            sharedPreferences = context.getSharedPreferences("Cache", Context.MODE_PRIVATE)
            editor = sharedPreferences!!.edit()
            Value?.let { editor?.putInt(Key, it) }
            editor?.commit()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    fun getInt_noti(contextGetKey: Context, Key: String?): Int {
        sharedPreferences = contextGetKey.getSharedPreferences("Cache", Context.MODE_PRIVATE)
        return sharedPreferences!!.getInt(Key,4)
    }

    fun clearSharedPreferences(context: Context) {
        sharedPreferences = context.getSharedPreferences("Cache", Context.MODE_PRIVATE)
    }
}
