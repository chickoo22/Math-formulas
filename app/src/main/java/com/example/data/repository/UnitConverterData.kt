package com.example.data.repository

import com.example.data.model.UnitCategory
import com.example.data.model.UnitDefinition

object UnitConverterData {

    val CATEGORY_UNITS: Map<UnitCategory, List<UnitDefinition>> = mapOf(
        UnitCategory.ANGLE to listOf(
            UnitDefinition("Degrees", "°", 1.0),
            UnitDefinition("Radians", "rad", 180.0 / Math.PI),
            UnitDefinition("Gradians", "grad", 0.9),
            UnitDefinition("Arcminutes", "arcmin", 1.0 / 60.0),
            UnitDefinition("Arcseconds", "arcsec", 1.0 / 3600.0),
            UnitDefinition("Turns / Revolutions", "tr", 360.0)
        ),
        UnitCategory.LENGTH to listOf(
            UnitDefinition("Meters", "m", 1.0),
            UnitDefinition("Kilometers", "km", 1000.0),
            UnitDefinition("Centimeters", "cm", 0.01),
            UnitDefinition("Millimeters", "mm", 0.001),
            UnitDefinition("Inches", "in", 0.0254),
            UnitDefinition("Feet", "ft", 0.3048),
            UnitDefinition("Yards", "yd", 0.9144),
            UnitDefinition("Miles", "mi", 1609.344),
            UnitDefinition("Nautical Miles", "NM", 1852.0)
        ),
        UnitCategory.AREA to listOf(
            UnitDefinition("Square Meters", "m²", 1.0),
            UnitDefinition("Square Kilometers", "km²", 1_000_000.0),
            UnitDefinition("Square Centimeters", "cm²", 0.0001),
            UnitDefinition("Square Feet", "ft²", 0.092903),
            UnitDefinition("Square Inches", "in²", 0.00064516),
            UnitDefinition("Hectares", "ha", 10_000.0),
            UnitDefinition("Acres", "ac", 4046.86)
        ),
        UnitCategory.VOLUME to listOf(
            UnitDefinition("Liters", "L", 1.0),
            UnitDefinition("Milliliters", "mL", 0.001),
            UnitDefinition("Cubic Meters", "m³", 1000.0),
            UnitDefinition("Cubic Centimeters", "cm³", 0.001),
            UnitDefinition("US Gallons", "gal (US)", 3.78541),
            UnitDefinition("Imperial Gallons", "gal (UK)", 4.54609),
            UnitDefinition("Cubic Feet", "ft³", 28.3168)
        ),
        UnitCategory.MASS to listOf(
            UnitDefinition("Kilograms", "kg", 1.0),
            UnitDefinition("Grams", "g", 0.001),
            UnitDefinition("Milligrams", "mg", 0.000001),
            UnitDefinition("Pounds", "lbs", 0.45359237),
            UnitDefinition("Ounces", "oz", 0.0283495),
            UnitDefinition("Metric Tons", "t", 1000.0)
        ),
        UnitCategory.TEMPERATURE to listOf(
            UnitDefinition("Celsius", "°C", 1.0, 0.0),
            UnitDefinition("Fahrenheit", "°F", 5.0 / 9.0, 32.0),
            UnitDefinition("Kelvin", "K", 1.0, 273.15)
        ),
        UnitCategory.NUMBER_BASE to listOf(
            UnitDefinition("Decimal", "Dec (Base 10)"),
            UnitDefinition("Binary", "Bin (Base 2)"),
            UnitDefinition("Hexadecimal", "Hex (Base 16)"),
            UnitDefinition("Octal", "Oct (Base 8)")
        ),
        UnitCategory.SPEED to listOf(
            UnitDefinition("Meters per second", "m/s", 1.0),
            UnitDefinition("Kilometers per hour", "km/h", 1.0 / 3.6),
            UnitDefinition("Miles per hour", "mph", 0.44704),
            UnitDefinition("Knots", "kn", 0.514444),
            UnitDefinition("Speed of Light", "c", 299_792_458.0)
        ),
        UnitCategory.TIME to listOf(
            UnitDefinition("Seconds", "s", 1.0),
            UnitDefinition("Minutes", "min", 60.0),
            UnitDefinition("Hours", "hr", 3600.0),
            UnitDefinition("Days", "d", 86400.0),
            UnitDefinition("Weeks", "wk", 604800.0),
            UnitDefinition("Julian Years", "yr", 31_557_600.0)
        )
    )

    fun convert(
        value: Double,
        fromUnit: UnitDefinition,
        toUnit: UnitDefinition,
        category: UnitCategory
    ): Double {
        if (category == UnitCategory.TEMPERATURE) {
            // Convert to Celsius base
            val inCelsius = when (fromUnit.name) {
                "Celsius" -> value
                "Fahrenheit" -> (value - 32.0) * (5.0 / 9.0)
                "Kelvin" -> value - 273.15
                else -> value
            }
            // Convert from Celsius to target
            return when (toUnit.name) {
                "Celsius" -> inCelsius
                "Fahrenheit" -> (inCelsius * (9.0 / 5.0)) + 32.0
                "Kelvin" -> inCelsius + 273.15
                else -> inCelsius
            }
        }

        // Standard multiplicative conversion via base unit
        val inBase = value * fromUnit.toBaseFactor
        return inBase / toUnit.toBaseFactor
    }

    fun convertNumberBase(
        inputStr: String,
        fromUnit: String,
        toUnit: String
    ): Result<String> {
        return runCatching {
            val fromRadix = when (fromUnit) {
                "Binary" -> 2
                "Octal" -> 8
                "Hexadecimal" -> 16
                else -> 10
            }
            val toRadix = when (toUnit) {
                "Binary" -> 2
                "Octal" -> 8
                "Hexadecimal" -> 16
                else -> 10
            }
            val cleanInput = inputStr.trim().replace(" ", "").uppercase()
            val decimalValue = cleanInput.toLong(fromRadix)
            decimalValue.toString(toRadix).uppercase()
        }
    }
}
