package com.galaxy.promotion.engine.objects

import com.galaxy.promotion.codegen.types.PromotionCondition
import com.galaxy.promotion.codegen.types.Operator
import java.lang.StringBuilder

class PERule {
    var conditions: List<PromotionCondition?> = listOf()
    var action: PEAction = PEAction()
    var requestClassName: String =""


    fun json(): String {
        return gson.toJson(this).replace("\"","'")
    }
    override fun toString(): String {
        val statementBuilder = StringBuilder()
        for (condition in conditions) {
            var operator = when (condition!!.operator.name) {
                Operator.EQUAL_TO.name -> "=="
                Operator.NOT_EQUAL_TO.name -> "!="
                Operator.GREATER_THAN.name -> ">"
                Operator.LESS_THAN.name -> "<"
                Operator.GREATER_THAN_OR_EQUAL_TO.name -> ">="
                Operator.LESS_THAN_OR_EQUAL_TO.name -> "<="
                Operator.IN.name -> "in"
                else ->  "=="
            }
            if(condition.operator!!.equals(Operator.IN) && condition.value is String){

                val inConditions = StringBuilder()
                inConditions.append(condition.field).append(" in (")

                val inValues= (condition.value as String).split(",");
                for (inValue in inValues) {
                    inConditions.append(" '").append(inValue).append("' ")
                    inConditions.append(" , ")
                }
                val inStatement = inConditions.toString()
                statementBuilder.append(inStatement.substring(0, inStatement.length - 4))
                statementBuilder.append(" ) ")


            }else {
                statementBuilder.append(condition.field).append(" ").append(operator).append(" ")

                if (condition.value is String) {
                    statementBuilder.append("'").append(condition.value).append("'")
                } else {
                    statementBuilder.append(condition.value)
                }
            }
            statementBuilder.append(" && ")
        }
        val statement = statementBuilder.toString()

        // remove trailing &&
        return statement.substring(0, statement.length - 4)
    }

}