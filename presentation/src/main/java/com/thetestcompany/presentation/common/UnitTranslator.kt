package com.thetestcompany.presentation.common

import android.content.res.Resources
import com.thetestcompany.domain.entities.UnitsOfQuantity
import com.thetestcompany.presentation.R

class UnitTranslator(resources: Resources) {
    /* units of measurements */
    val unit_measurement = resources.getStringArray(R.array.units)

    fun translate(unit: UnitsOfQuantity): String {
        return when (unit) {
            UnitsOfQuantity.ML -> unit_measurement[0]
            UnitsOfQuantity.CL -> unit_measurement[1]
            UnitsOfQuantity.DL -> unit_measurement[2]
            UnitsOfQuantity.L  -> unit_measurement[3]
            UnitsOfQuantity.G  -> unit_measurement[4]
            UnitsOfQuantity.HG -> unit_measurement[5]
            UnitsOfQuantity.KG -> unit_measurement[6]
            UnitsOfQuantity.ST -> unit_measurement[7]
            else               -> unit_measurement[8]
        }
    }

    fun toUnitsOfQuantity(unitId: Int): UnitsOfQuantity {
        return when (unitId) {
            0 -> UnitsOfQuantity.ML
            1 -> UnitsOfQuantity.CL
            2 -> UnitsOfQuantity.DL
            3 -> UnitsOfQuantity.L
            4 -> UnitsOfQuantity.G
            5 -> UnitsOfQuantity.HG
            6 -> UnitsOfQuantity.KG
            7 -> UnitsOfQuantity.ST
            else -> UnitsOfQuantity.PK

        }
    }
}