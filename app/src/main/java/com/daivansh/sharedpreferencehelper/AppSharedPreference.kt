package com.daivansh.sharedpreferencehelper

import android.content.Context
import android.content.SharedPreferences
import android.util.LruCache

object AppSharedPreference{

    private const val MAX_CACHE_LIMIT = 5
    private val cache = LruCache<String, SharedPreferences>(MAX_CACHE_LIMIT)
    private var preference: SharedPreferences?= null

//    val PREFERENCE_NAME = "app_shared_preferences"

//    fun init(context: Context) {
//        preference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
//    }

    inline fun <reified T> getValue(context: Context,preferenceName: String,key: String, defaultValue: T): T{
        return getPreference(context,preferenceName).get(key,defaultValue)
    }

    inline fun <reified T> putValue(context: Context,preferenceName: String,key: String, value: T) {
        getPreference(context,preferenceName).put(key,value)
    }

    fun getPreference(context: Context, preferenceName: String): SharedPreferences {
        preference = cache.get(preferenceName)
        return preference ?: synchronized(this){
            preference = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE)
            cache.put(preferenceName, preference)
            preference!!
        }
    }

}

inline fun <reified T> SharedPreferences.get(key: String, defaultValue: T): T {
    when(T::class) {
        Boolean::class -> return this.getBoolean(key, defaultValue as Boolean) as T
        Float::class -> return this.getFloat(key, defaultValue as Float) as T
        Int::class -> return this.getInt(key, defaultValue as Int) as T
        Long::class -> return this.getLong(key, defaultValue as Long) as T
        String::class -> return this.getString(key, defaultValue as String) as T
        else -> {
            if (defaultValue is Set<*>) {
                return this.getStringSet(key, defaultValue as Set<String>) as T
            }
        }
    }

    return defaultValue
}

inline fun <reified T> SharedPreferences.put(key: String, value: T) {
    val editor = this.edit()

    when(T::class) {
        Boolean::class -> editor.putBoolean(key, value as Boolean)
        Float::class -> editor.putFloat(key, value as Float)
        Int::class -> editor.putInt(key, value as Int)
        Long::class -> editor.putLong(key, value as Long)
        String::class -> editor.putString(key, value as String)
        else -> {
            if (value is Set<*>) {
                editor.putStringSet(key, value as Set<String>)
            }
        }
    }

    editor.apply()
}