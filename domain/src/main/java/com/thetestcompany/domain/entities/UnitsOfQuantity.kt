package com.thetestcompany.domain.entities


enum class UnitsOfQuantity(val id: Int) {
    ML(0),
    CL(1),
    DL(2),
    L(3),
    G(4),
    HG(5),
    KG(6),
    ST(7),
    PK(8);

    companion object {
        private val map = UnitsOfQuantity.values()
        fun fromInt(type: Int): UnitsOfQuantity {
            return map[type]
        }
    }
}