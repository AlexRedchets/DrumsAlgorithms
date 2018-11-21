package com.aredchets.drumsalgorithms

/**
 * @author Alex Redchets
 */

fun String.filterForNumber() : Long {
    val re = Regex("[^0-9]")
    return re.replace(this, "").toLong()
}
 