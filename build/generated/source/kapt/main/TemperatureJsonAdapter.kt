// Code generated by moshi-kotlin-codegen. Do not edit.
@file:Suppress("DEPRECATION", "unused", "ClassName", "REDUNDANT_PROJECTION",
    "RedundantExplicitType", "LocalVariableName", "RedundantVisibilityModifier",
    "PLATFORM_CLASS_MAPPED_TO_KOTLIN")

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.`internal`.Util
import java.lang.NullPointerException
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.emptySet
import kotlin.text.buildString

public class TemperatureJsonAdapter(
  moshi: Moshi
) : JsonAdapter<Temperature>() {
  private val options: JsonReader.Options = JsonReader.Options.of("main")

  private val mainAdapter: JsonAdapter<Main> = moshi.adapter(Main::class.java, emptySet(), "main")

  public override fun toString(): String = buildString(33) {
      append("GeneratedJsonAdapter(").append("Temperature").append(')') }

  public override fun fromJson(reader: JsonReader): Temperature {
    var main: Main? = null
    reader.beginObject()
    while (reader.hasNext()) {
      when (reader.selectName(options)) {
        0 -> main = mainAdapter.fromJson(reader) ?: throw Util.unexpectedNull("main", "main",
            reader)
        -1 -> {
          // Unknown name, skip it.
          reader.skipName()
          reader.skipValue()
        }
      }
    }
    reader.endObject()
    return Temperature(
        main = main ?: throw Util.missingProperty("main", "main", reader)
    )
  }

  public override fun toJson(writer: JsonWriter, value_: Temperature?): Unit {
    if (value_ == null) {
      throw NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.")
    }
    writer.beginObject()
    writer.name("main")
    mainAdapter.toJson(writer, value_.main)
    writer.endObject()
  }
}
