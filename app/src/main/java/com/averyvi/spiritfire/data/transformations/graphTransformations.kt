package com.averyvi.spiritfire.data.transformations

val Float.degreeToAngle
    get() = (this * Math.PI / 180f).toFloat()