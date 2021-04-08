package uz.hashteam.turontask.util

import android.content.Context
import android.content.SharedPreferences

class Prefs(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("Turon Task", Context.MODE_PRIVATE)


    fun save(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }

    fun save(key: String, value: Boolean) {
        prefs.edit().putBoolean(key, value).apply()
    }

    fun save(key: String, value: Int) {
        prefs.edit().putInt(key, value).apply()
    }

    fun save(key: String, value: Long) {
        prefs.edit().putLong(key, value).apply()
    }

    fun save(key: String, value: Float) {
        prefs.edit().putFloat(key, value).apply()
    }

    fun get(key: String, default: String) = prefs.getString(key, default)

    fun get(key: String, default: Boolean) = prefs.getBoolean(key, default)

    fun get(key: String, default: Int) = prefs.getInt(key, default)

    fun get(key: String, default: Long) = prefs.getLong(key, default)

    fun get(key: String, default: Float) = prefs.getFloat(key, default)

    fun clear() {
        prefs.edit().clear().apply()
    }

}