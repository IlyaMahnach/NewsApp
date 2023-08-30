package com.example.newsapp.utils

import android.annotation.SuppressLint
import com.example.newsapp.Constants.Companion.DEFAULT_DATE_FORMAT
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@ExperimentalSerializationApi
@Serializer(forClass = Date::class)
object DateSerializer : KSerializer<Date> {
    @SuppressLint("SimpleDateFormat")
    private val df: DateFormat = SimpleDateFormat(DEFAULT_DATE_FORMAT)

    override fun deserialize(decoder: Decoder): Date {
        return df.parse(decoder.decodeString())
    }

    override fun serialize(encoder: Encoder, value: Date) {
        encoder.encodeString(df.format(value))
    }
}