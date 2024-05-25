package com.timurkhabibulin.testandroid.domain.entities

import com.google.gson.annotations.SerializedName

data class Exif(
    val make: String,
    val model: String,
    val name: String,
    @SerializedName("exposure_time")
    val exposureTime: String,
    val aperture: String,
    @SerializedName("focal_length")
    val focalLength: String,
    val iso: Int
) {
    companion object {
        val Default = Exif(
            "Canon",
            "Canon EOS 40D",
            "Canon, EOS 40D",
            "0.1112",
            "4.970854",
            "37",
            100
        )
    }
}
