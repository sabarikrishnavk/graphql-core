package com.galaxy.promotion.engine.objects

import java.util.HashMap
import java.lang.IllegalArgumentException

class PECondition {
    var field: String? = null
    var value: Any? = null
    var operator: Operator? = null

    enum class Operator(private val value: String) {
        NOT_EQUAL_TO("NOT_EQUAL_TO"),
        EQUAL_TO("EQUAL_TO"),
        GREATER_THAN("GREATER_THAN"),
        LESS_THAN("LESS_THAN"),
        GREATER_THAN_OR_EQUAL_TO("GREATER_THAN_OR_EQUAL_TO"),
        LESS_THAN_OR_EQUAL_TO("LESS_THAN_OR_EQUAL_TO"),
        IN("IN");

        companion object {
            private val constants: MutableMap<String, Operator> = HashMap()
            fun fromValue(value: String): Operator {
                val constant = constants[value]
                return constant ?: throw IllegalArgumentException(value)
            }

            init {
                for (c in values()) {
                    constants[c.value] = c
                }
            }
        }

        override fun toString(): String {
            return value
        }
    }
}