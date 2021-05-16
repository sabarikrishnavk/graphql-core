package com.galaxy.promotion.engine

import java.lang.StringBuilder

class PERule {
    var conditions: List<PECondition>? = null
    var action: PEAction? =null
    var requestClassName: String =""

    override fun toString(): String {
        val statementBuilder = StringBuilder()
        for (condition in conditions!!) {
            var operator: String? = null
            operator = when (condition?.operator) {
                PECondition.Operator.EQUAL_TO -> "=="
                PECondition.Operator.NOT_EQUAL_TO -> "!="
                PECondition.Operator.GREATER_THAN -> ">"
                PECondition.Operator.LESS_THAN -> "<"
                PECondition.Operator.GREATER_THAN_OR_EQUAL_TO -> ">="
                PECondition.Operator.LESS_THAN_OR_EQUAL_TO -> "<="
                else ->  "=="
            }
            statementBuilder.append(condition.field).append(" ").append(operator).append(" ")
            if (condition.value is String) {
                statementBuilder.append("'").append(condition.value).append("'")
            } else {
                statementBuilder.append(condition.value)
            }
            statementBuilder.append(" && ")
        }
        val statement = statementBuilder.toString()

        // remove trailing &&
        return statement.substring(0, statement.length - 4)
    }

}