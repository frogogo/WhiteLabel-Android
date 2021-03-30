package ru.poprobuy.poprobuy.util

import android.content.SharedPreferences
import com.squareup.moshi.Moshi
import ru.poprobuy.poprobuy.extension.fromJsonOrNull
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

fun SharedPreferences.boolean(key: String, defValue: Boolean = false): BooleanItem = BooleanItem(this, key, defValue)

fun SharedPreferences.int(key: String, defValue: Int = 0): IntItem = IntItem(this, key, defValue)

fun SharedPreferences.long(key: String, defValue: Long = 0): LongItem = LongItem(this, key, defValue)

fun SharedPreferences.string(key: String, defValue: String = ""): StringItem = StringItem(this, key, defValue)

fun SharedPreferences.stringNullable(key: String): StringItemNullable = StringItemNullable(this, key)

fun <T : Any> SharedPreferences.json(moshi: Moshi, key: String, clazz: KClass<T>): JsonItem<T> =
  JsonItem(this, moshi, key, clazz)

class BooleanItem(
  private val preferences: SharedPreferences,
  private val key: String,
  private val defValue: Boolean,
) {

  operator fun getValue(thisRef: Any?, property: KProperty<*>): Boolean {
    return preferences.getBoolean(key, defValue)
  }

  operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Boolean) {
    preferences.edit().putBoolean(key, value).apply()
  }
}

class IntItem(
  private val preferences: SharedPreferences,
  private val key: String,
  private val defValue: Int,
) {

  operator fun getValue(thisRef: Any?, property: KProperty<*>): Int {
    return preferences.getInt(key, defValue)
  }

  operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
    preferences.edit().putInt(key, value).apply()
  }
}

class LongItem(
  private val preferences: SharedPreferences,
  private val key: String,
  private val defValue: Long,
) {

  operator fun getValue(thisRef: Any?, property: KProperty<*>): Long {
    return preferences.getLong(key, defValue)
  }

  operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Long) {
    preferences.edit().putLong(key, value).apply()
  }
}

class StringItem(
  private val preferences: SharedPreferences,
  private val key: String,
  private val defValue: String,
) {

  operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
    return preferences.getString(key, defValue).orEmpty()
  }

  operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
    preferences.edit().putString(key, value).apply()
  }
}

class StringItemNullable(private val preferences: SharedPreferences, private val key: String) {

  operator fun getValue(thisRef: Any?, property: KProperty<*>): String? {
    return preferences.getString(key, null)
  }

  operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String?) {
    preferences.edit().putString(key, value).apply()
  }
}

class JsonItem<T : Any>(
  private val preferences: SharedPreferences,
  private val moshi: Moshi,
  private val key: String,
  private val clazz: KClass<T>,
) {

  operator fun getValue(thisRef: Any?, property: KProperty<*>): T? {
    val jsonValue = preferences.getString(key, null)
    jsonValue ?: return null
    return moshi.adapter(clazz.java).fromJsonOrNull(jsonValue)
  }

  operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
    val jsonValue = moshi.adapter(clazz.java).toJson(value)
    preferences.edit().putString(key, jsonValue).apply()
  }
}
