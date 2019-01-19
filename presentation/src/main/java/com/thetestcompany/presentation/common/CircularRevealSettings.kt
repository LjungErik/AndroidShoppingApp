package com.thetestcompany.presentation.common

import android.os.Parcel
import android.os.Parcelable

class CircularRevealSettings(val centerX: Int, val centerY: Int, val width: Int, val height: Int, val duration: Long) : Parcelable {
    constructor(parcel: Parcel) : this(
            centerX = parcel.readInt(),
            centerY = parcel.readInt(),
            width = parcel.readInt(),
            height = parcel.readInt(),
            duration = parcel.readLong())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(centerX)
        parcel.writeInt(centerY)
        parcel.writeInt(width)
        parcel.writeInt(height)
        parcel.writeLong(duration)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CircularRevealSettings> {
        override fun createFromParcel(parcel: Parcel): CircularRevealSettings {
            return CircularRevealSettings(parcel)
        }

        override fun newArray(size: Int): Array<CircularRevealSettings?> {
            return arrayOfNulls(size)
        }
    }
}