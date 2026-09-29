package com.example.data.model

enum class UnitCategory(val displayName: String) {
    ANGLE("Angle"),
    LENGTH("Length"),
    AREA("Area"),
    VOLUME("Volume"),
    MASS("Mass / Weight"),
    TEMPERATURE("Temperature"),
    NUMBER_BASE("Number Base"),
    SPEED("Speed"),
    TIME("Time")
}

data class ConversionUnit(
    val id: String,
    val name: String,
    val symbol: String,
    val toBaseFactor: Double = 1.0 // relative to standard base (e.g. meter, radian, kg, etc.)
)

object UnitConverterData {

    val CATEGORIES = listOf(
        UnitCategory.ANGLE,
        UnitCategory.LENGTH,
        UnitCategory.AREA,
        UnitCategory.VOLUME,
        UnitCategory.MASS,
        UnitCategory.TEMPERATURE,
        UnitCategory.NUMBER_BASE,
        UnitCategory.SPEED,
        UnitCategory.TIME
    )

    fun getUnitsForCategory(category: UnitCategory): List<ConversionUnit> = when (category) {
        UnitCategory.ANGLE -> listOf(
            ConversionUnit("deg", "Degree", "°", 1.0),
            ConversionUnit("rad", "Radian", "rad", 180.0 / Math.PI),
            ConversionUnit("grad", "Gradian", "grad", 0.9),
            ConversionUnit("arcmin", "Arcminute", "′", 1.0 / 60.0),
            ConversionUnit("arcsec", "Arcsecond", "″", 1.0 / 3600.0)
        )
        UnitCategory.LENGTH -> listOf(
            ConversionUnit("m", "Meter", "m", 1.0),
            ConversionUnit("km", "Kilometer", "km", 1000.0),
            ConversionUnit("cm", "Centimeter", "cm", 0.01),
            ConversionUnit("mm", "Millimeter", "mm", 0.001),
            ConversionUnit("mi", "Mile", "mi", 1609.344),
            ConversionUnit("yd", "Yard", "yd", 0.9144),
            ConversionUnit("ft", "Foot", "ft", 0.3048),
            ConversionUnit("in", "Inch", "in", 0.0254),
            ConversionUnit("nmi", "Nautical Mile", "NM", 1852.0)
        )
        UnitCategory.AREA -> listOf(
            ConversionUnit("sq_m", "Square Meter", "m²", 1.0),
            ConversionUnit("sq_km", "Square Kilometer", "km²", 1_000_000.0),
            ConversionUnit("sq_cm", "Square Centimeter", "cm²", 0.0001),
            ConversionUnit("sq_ft", "Square Foot", "ft²", 0.09290304),
            ConversionUnit("sq_in", "Square Inch", "in²", 0.00064516),
            ConversionUnit("acre", "Acre", "ac", 4046.8564224),
            ConversionUnit("hectare", "Hectare", "ha", 10000.0)
        )
        UnitCategory.VOLUME -> listOf(
            ConversionUnit("l", "Liter", "L", 1.0),
            ConversionUnit("ml", "Milliliter", "mL", 0.001),
            ConversionUnit("cu_m", "Cubic Meter", "m³", 1000.0),
            ConversionUnit("gal_us", "Gallon (US)", "gal", 3.785411784),
            ConversionUnit("fl_oz", "Fluid Ounce (US)", "fl oz", 0.0295735),
            ConversionUnit("cu_ft", "Cubic Foot", "ft³", 28.316846592)
        )
        UnitCategory.MASS -> listOf(
            ConversionUnit("kg", "Kilogram", "kg", 1.0),
            ConversionUnit("g", "Gram", "g", 0.001),
            ConversionUnit("mg", "Milligram", "mg", 0.000001),
            ConversionUnit("lb", "Pound", "lb", 0.45359237),
            ConversionUnit("oz", "Ounce", "oz", 0.028349523125),
            ConversionUnit("t", "Metric Ton", "t", 1000.0)
        )
        UnitCategory.TEMPERATURE -> listOf(
            ConversionUnit("c", "Celsius", "°C"),
            ConversionUnit("f", "Fahrenheit", "°F"),
            ConversionUnit("k", "Kelvin", "K")
        )
        UnitCategory.NUMBER_BASE -> listOf(
            ConversionUnit("dec", "Decimal", "Base 10"),
            ConversionUnit("bin", "Binary", "Base 2"),
            ConversionUnit("oct", "Octal", "Base 8"),
            ConversionUnit("hex", "Hexadecimal", "Base 16")
        )
        UnitCategory.SPEED -> listOf(
            ConversionUnit("mps", "Meter per second", "m/s", 1.0),
            ConversionUnit("kmh", "Kilometer per hour", "km/h", 1.0 / 3.6),
            ConversionUnit("mph", "Mile per hour", "mph", 0.44704),
            ConversionUnit("knot", "Knot", "kn", 0.514444),
            ConversionUnit("fps", "Foot per second", "ft/s", 0.3048)
        )
        UnitCategory.TIME -> listOf(
            ConversionUnit("s", "Second", "s", 1.0),
            ConversionUnit("ms", "Millisecond", "ms", 0.001),
            ConversionUnit("min", "Minute", "min", 60.0),
            ConversionUnit("h", "Hour", "h", 3600.0),
            ConversionUnit("d", "Day", "d", 86400.0),
            ConversionUnit("w", "Week", "wk", 604800.0),
            ConversionUnit("y", "Year (365d)", "yr", 31536000.0)
        )
    }

    fun convert(valueStr: String, from: ConversionUnit, to: ConversionUnit, category: UnitCategory): String {
        if (valueStr.isBlank()) return ""

        if (category == UnitCategory.NUMBER_BASE) {
            return try {
                val fromRadix = when (from.id) {
                    "bin" -> 2
                    "oct" -> 8
                    "hex" -> 16
                    else -> 10
                }
                val toRadix = when (to.id) {
                    "bin" -> 2
                    "oct" -> 8
                    "hex" -> 16
                    else -> 10
                }
                val decValue = valueStr.trim().toLong(fromRadix)
                decValue.toString(toRadix).uppercase()
            } catch (e: Exception) {
                "Invalid"
            }
        }

        val value = valueStr.toDoubleOrNull() ?: return "Invalid"

        if (category == UnitCategory.TEMPERATURE) {
            // Convert to Celsius first
            val inCelsius = when (from.id) {
                "c" -> value
                "f" -> (value - 32.0) * 5.0 / 9.0
                "k" -> value - 273.15
                else -> value
            }
            val result = when (to.id) {
                "c" -> inCelsius
                "f" -> (inCelsius * 9.0 / 5.0) + 32.0
                "k" -> inCelsius + 273.15
                else -> inCelsius
            }
            return String.format("%.4f", result).trimEnd('0').trimEnd('.')
        }

        // Standard linear conversion: value * from.toBaseFactor / to.toBaseFactor
        val inBase = value * from.toBaseFactor
        val result = inBase / to.toBaseFactor
        return if (kotlin.math.abs(result) >= 1e9 || (kotlin.math.abs(result) > 0 && kotlin.math.abs(result) < 1e-4)) {
            String.format("%.6e", result)
        } else {
            String.format("%.6f", result).trimEnd('0').trimEnd('.')
        }
    }
}
