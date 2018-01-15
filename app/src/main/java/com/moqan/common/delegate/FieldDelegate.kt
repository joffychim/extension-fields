package com.moqan.common.delegate

import com.moqan.common.collection.ConcurrentWeakHashMap
import java.util.concurrent.ConcurrentHashMap
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @author joffychim
 */
private val fields = ConcurrentWeakHashMap<Any, ConcurrentHashMap<KProperty<*>, Any>>()

class FieldDelegate<in T : Any, R>(private val valueFactory: T.()->R) : ReadWriteProperty<T, R> {
    private fun getValue(thisRef: T) = valueFactory.invoke(thisRef) ?: noneValue
    private fun setValue(thisRef: T, property: KProperty<*>, value: Any, putIfAbsent: Boolean) {
        var properties = fields[thisRef]
        if (properties === null) {
            properties = ConcurrentHashMap()
        }
        val oldProperties = fields.putIfAbsent(thisRef, properties)
        if (oldProperties != null) properties = oldProperties
        if (putIfAbsent) {
            properties.putIfAbsent(property, value)
        } else {
            properties[property] = value
        }
    }

    override fun setValue(thisRef: T, property: KProperty<*>, value: R) {
        setValue(thisRef, property, value ?: noneValue, false)
    }

    override fun getValue(thisRef: T, property: KProperty<*>): R {
        val properties = fields[thisRef]
        var value = properties?.get(property)
        if (value == null) {
            value = getValue(thisRef)
            setValue(thisRef, property, value, true)
        }

        @Suppress("UNCHECKED_CAST")
        return if (value === noneValue) null as R else value as R
    }
}

internal val noneValue = Any()
// 直接给值会让多个对象共享一个field，不合理
// fun <T : Any, R> field(value: R) = FieldDelegate<T, R>({value})
fun <T : Any, R> field(valueFactory: T.()->R) = FieldDelegate<T, R>(valueFactory)

