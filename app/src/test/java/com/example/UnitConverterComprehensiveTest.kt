package com.example

import com.example.data.model.UnitCategory
import com.example.data.model.UnitDefinition
import com.example.data.repository.UnitConverterData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.math.PI

class UnitConverterComprehensiveTest {

    private fun getUnit(category: UnitCategory, name: String): UnitDefinition {
        val list = UnitConverterData.CATEGORY_UNITS[category]
        assertNotNull("Category $category should have units", list)
        val unit = list?.firstOrNull { it.name == name }
        assertNotNull("Unit '$name' not found in $category", unit)
        return unit!!
    }

    @Test
    fun testAllCategoriesPresentWithUnits() {
        val categories = UnitCategory.entries
        assertEquals("Should have exactly 9 conversion categories", 9, categories.size)
        for (cat in categories) {
            val units = UnitConverterData.CATEGORY_UNITS[cat]
            assertNotNull("Category ${cat.displayName} must be mapped in CATEGORY_UNITS", units)
            assertTrue("Category ${cat.displayName} must have at least 3 units", (units?.size ?: 0) >= 3)
            units?.forEach { u ->
                assertTrue("Unit name must not be blank", u.name.isNotBlank())
                assertTrue("Unit symbol must not be blank", u.symbol.isNotBlank())
            }
        }
    }

    @Test
    fun testAngleConversions() {
        val deg = getUnit(UnitCategory.ANGLE, "Degrees")
        val rad = getUnit(UnitCategory.ANGLE, "Radians")
        val grad = getUnit(UnitCategory.ANGLE, "Gradians")
        val arcmin = getUnit(UnitCategory.ANGLE, "Arcminutes")
        val arcsec = getUnit(UnitCategory.ANGLE, "Arcseconds")
        val turns = getUnit(UnitCategory.ANGLE, "Turns / Revolutions")

        // 180 degrees = PI radians
        assertEquals(PI, UnitConverterData.convert(180.0, deg, rad, UnitCategory.ANGLE), 1e-6)
        // PI radians = 180 degrees
        assertEquals(180.0, UnitConverterData.convert(PI, rad, deg, UnitCategory.ANGLE), 1e-6)
        // 90 degrees = 100 gradians
        assertEquals(100.0, UnitConverterData.convert(90.0, deg, grad, UnitCategory.ANGLE), 1e-6)
        // 1 degree = 60 arcminutes
        assertEquals(60.0, UnitConverterData.convert(1.0, deg, arcmin, UnitCategory.ANGLE), 1e-6)
        // 1 degree = 3600 arcseconds
        assertEquals(3600.0, UnitConverterData.convert(1.0, deg, arcsec, UnitCategory.ANGLE), 1e-6)
        // 360 degrees = 1 turn
        assertEquals(1.0, UnitConverterData.convert(360.0, deg, turns, UnitCategory.ANGLE), 1e-6)
        // Identity conversion
        assertEquals(45.0, UnitConverterData.convert(45.0, deg, deg, UnitCategory.ANGLE), 1e-6)
    }

    @Test
    fun testLengthConversions() {
        val m = getUnit(UnitCategory.LENGTH, "Meters")
        val km = getUnit(UnitCategory.LENGTH, "Kilometers")
        val cm = getUnit(UnitCategory.LENGTH, "Centimeters")
        val mm = getUnit(UnitCategory.LENGTH, "Millimeters")
        val inch = getUnit(UnitCategory.LENGTH, "Inches")
        val ft = getUnit(UnitCategory.LENGTH, "Feet")
        val yd = getUnit(UnitCategory.LENGTH, "Yards")
        val mi = getUnit(UnitCategory.LENGTH, "Miles")
        val nmi = getUnit(UnitCategory.LENGTH, "Nautical Miles")

        // Metric
        assertEquals(1000.0, UnitConverterData.convert(1.0, km, m, UnitCategory.LENGTH), 1e-6)
        assertEquals(100.0, UnitConverterData.convert(1.0, m, cm, UnitCategory.LENGTH), 1e-6)
        assertEquals(1000.0, UnitConverterData.convert(1.0, m, mm, UnitCategory.LENGTH), 1e-6)

        // Imperial
        assertEquals(2.54, UnitConverterData.convert(1.0, inch, cm, UnitCategory.LENGTH), 1e-6)
        assertEquals(12.0, UnitConverterData.convert(1.0, ft, inch, UnitCategory.LENGTH), 1e-6)
        assertEquals(3.0, UnitConverterData.convert(1.0, yd, ft, UnitCategory.LENGTH), 1e-6)
        assertEquals(1609.344, UnitConverterData.convert(1.0, mi, m, UnitCategory.LENGTH), 1e-3)
        assertEquals(1852.0, UnitConverterData.convert(1.0, nmi, m, UnitCategory.LENGTH), 1e-3)
    }

    @Test
    fun testAreaConversions() {
        val sqM = getUnit(UnitCategory.AREA, "Square Meters")
        val sqKm = getUnit(UnitCategory.AREA, "Square Kilometers")
        val sqCm = getUnit(UnitCategory.AREA, "Square Centimeters")
        val sqFt = getUnit(UnitCategory.AREA, "Square Feet")
        val sqIn = getUnit(UnitCategory.AREA, "Square Inches")
        val ha = getUnit(UnitCategory.AREA, "Hectares")
        val ac = getUnit(UnitCategory.AREA, "Acres")

        // 1 km² = 1,000,000 m²
        assertEquals(1_000_000.0, UnitConverterData.convert(1.0, sqKm, sqM, UnitCategory.AREA), 1e-2)
        // 1 ha = 10,000 m²
        assertEquals(10_000.0, UnitConverterData.convert(1.0, ha, sqM, UnitCategory.AREA), 1e-2)
        // 1 m² = 10,000 cm²
        assertEquals(10_000.0, UnitConverterData.convert(1.0, sqM, sqCm, UnitCategory.AREA), 1e-2)
        // 1 ft² = 144 in²
        assertEquals(144.0, UnitConverterData.convert(1.0, sqFt, sqIn, UnitCategory.AREA), 1e-2)
        // 1 acre ≈ 4046.86 m²
        assertEquals(4046.86, UnitConverterData.convert(1.0, ac, sqM, UnitCategory.AREA), 1.0)
    }

    @Test
    fun testVolumeConversions() {
        val l = getUnit(UnitCategory.VOLUME, "Liters")
        val ml = getUnit(UnitCategory.VOLUME, "Milliliters")
        val cuM = getUnit(UnitCategory.VOLUME, "Cubic Meters")
        val cuCm = getUnit(UnitCategory.VOLUME, "Cubic Centimeters")
        val galUs = getUnit(UnitCategory.VOLUME, "US Gallons")
        val galUk = getUnit(UnitCategory.VOLUME, "Imperial Gallons")

        // 1 m³ = 1000 L
        assertEquals(1000.0, UnitConverterData.convert(1.0, cuM, l, UnitCategory.VOLUME), 1e-4)
        // 1 L = 1000 mL
        assertEquals(1000.0, UnitConverterData.convert(1.0, l, ml, UnitCategory.VOLUME), 1e-4)
        // 1 cm³ = 1 mL
        assertEquals(1.0, UnitConverterData.convert(1.0, cuCm, ml, UnitCategory.VOLUME), 1e-4)
        // 1 US Gallon ≈ 3.78541 L
        assertEquals(3.78541, UnitConverterData.convert(1.0, galUs, l, UnitCategory.VOLUME), 1e-4)
        // 1 Imperial Gallon ≈ 4.54609 L
        assertEquals(4.54609, UnitConverterData.convert(1.0, galUk, l, UnitCategory.VOLUME), 1e-4)
    }

    @Test
    fun testMassConversions() {
        val kg = getUnit(UnitCategory.MASS, "Kilograms")
        val g = getUnit(UnitCategory.MASS, "Grams")
        val mg = getUnit(UnitCategory.MASS, "Milligrams")
        val lb = getUnit(UnitCategory.MASS, "Pounds")
        val oz = getUnit(UnitCategory.MASS, "Ounces")
        val ton = getUnit(UnitCategory.MASS, "Metric Tons")

        assertEquals(1000.0, UnitConverterData.convert(1.0, kg, g, UnitCategory.MASS), 1e-6)
        assertEquals(1000.0, UnitConverterData.convert(1.0, g, mg, UnitCategory.MASS), 1e-6)
        assertEquals(1000.0, UnitConverterData.convert(1.0, ton, kg, UnitCategory.MASS), 1e-6)
        assertEquals(0.45359237, UnitConverterData.convert(1.0, lb, kg, UnitCategory.MASS), 1e-6)
        assertEquals(16.0, UnitConverterData.convert(1.0, lb, oz, UnitCategory.MASS), 1e-2)
    }

    @Test
    fun testTemperatureConversions() {
        val c = getUnit(UnitCategory.TEMPERATURE, "Celsius")
        val f = getUnit(UnitCategory.TEMPERATURE, "Fahrenheit")
        val k = getUnit(UnitCategory.TEMPERATURE, "Kelvin")

        // Freezing point of water
        assertEquals(32.0, UnitConverterData.convert(0.0, c, f, UnitCategory.TEMPERATURE), 1e-4)
        assertEquals(0.0, UnitConverterData.convert(32.0, f, c, UnitCategory.TEMPERATURE), 1e-4)
        assertEquals(273.15, UnitConverterData.convert(0.0, c, k, UnitCategory.TEMPERATURE), 1e-4)

        // Boiling point of water
        assertEquals(212.0, UnitConverterData.convert(100.0, c, f, UnitCategory.TEMPERATURE), 1e-4)
        assertEquals(100.0, UnitConverterData.convert(212.0, f, c, UnitCategory.TEMPERATURE), 1e-4)
        assertEquals(373.15, UnitConverterData.convert(100.0, c, k, UnitCategory.TEMPERATURE), 1e-4)

        // Cross-over equality point (-40°C == -40°F)
        assertEquals(-40.0, UnitConverterData.convert(-40.0, c, f, UnitCategory.TEMPERATURE), 1e-4)
        assertEquals(-40.0, UnitConverterData.convert(-40.0, f, c, UnitCategory.TEMPERATURE), 1e-4)

        // Absolute zero
        assertEquals(0.0, UnitConverterData.convert(-273.15, c, k, UnitCategory.TEMPERATURE), 1e-4)
        assertEquals(-273.15, UnitConverterData.convert(0.0, k, c, UnitCategory.TEMPERATURE), 1e-4)
    }

    @Test
    fun testNumberBaseConversions() {
        // Decimal to Hexadecimal
        val hexRes = UnitConverterData.convertNumberBase("255", "Decimal", "Hexadecimal")
        assertTrue(hexRes.isSuccess)
        assertEquals("FF", hexRes.getOrNull())

        // Hexadecimal to Decimal
        val decRes = UnitConverterData.convertNumberBase("FF", "Hexadecimal", "Decimal")
        assertTrue(decRes.isSuccess)
        assertEquals("255", decRes.getOrNull())

        // Decimal to Binary
        val binRes = UnitConverterData.convertNumberBase("255", "Decimal", "Binary")
        assertTrue(binRes.isSuccess)
        assertEquals("11111111", binRes.getOrNull())

        // Binary to Decimal
        val binToDec = UnitConverterData.convertNumberBase("1010", "Binary", "Decimal")
        assertTrue(binToDec.isSuccess)
        assertEquals("10", binToDec.getOrNull())

        // Decimal to Octal
        val octRes = UnitConverterData.convertNumberBase("64", "Decimal", "Octal")
        assertTrue(octRes.isSuccess)
        assertEquals("100", octRes.getOrNull())

        // Binary to Hexadecimal
        val binToHex = UnitConverterData.convertNumberBase("11110000", "Binary", "Hexadecimal")
        assertTrue(binToHex.isSuccess)
        assertEquals("F0", binToHex.getOrNull())

        // Whitespace and lowercase handling
        val whitespaceHex = UnitConverterData.convertNumberBase("  ab  ", "Hexadecimal", "Decimal")
        assertTrue(whitespaceHex.isSuccess)
        assertEquals("171", whitespaceHex.getOrNull())

        // Invalid input handling
        val invalidBin = UnitConverterData.convertNumberBase("12A", "Binary", "Decimal")
        assertTrue("Invalid binary digit should fail safely", invalidBin.isFailure)

        val invalidHex = UnitConverterData.convertNumberBase("XYZ", "Hexadecimal", "Decimal")
        assertTrue("Invalid hex digit should fail safely", invalidHex.isFailure)
    }

    @Test
    fun testSpeedConversions() {
        val mps = getUnit(UnitCategory.SPEED, "Meters per second")
        val kmh = getUnit(UnitCategory.SPEED, "Kilometers per hour")
        val mph = getUnit(UnitCategory.SPEED, "Miles per hour")
        val kn = getUnit(UnitCategory.SPEED, "Knots")
        val c = getUnit(UnitCategory.SPEED, "Speed of Light")

        // 1 m/s = 3.6 km/h
        assertEquals(3.6, UnitConverterData.convert(1.0, mps, kmh, UnitCategory.SPEED), 1e-4)
        // 100 km/h ≈ 27.7778 m/s
        assertEquals(27.7778, UnitConverterData.convert(100.0, kmh, mps, UnitCategory.SPEED), 1e-3)
        // 60 mph ≈ 96.5606 km/h
        assertEquals(96.5606, UnitConverterData.convert(60.0, mph, kmh, UnitCategory.SPEED), 1e-2)
        // 1 knot ≈ 1.852 km/h
        assertEquals(1.852, UnitConverterData.convert(1.0, kn, kmh, UnitCategory.SPEED), 1e-3)
        // Speed of light in m/s
        assertEquals(299_792_458.0, UnitConverterData.convert(1.0, c, mps, UnitCategory.SPEED), 1.0)
    }

    @Test
    fun testTimeConversions() {
        val s = getUnit(UnitCategory.TIME, "Seconds")
        val min = getUnit(UnitCategory.TIME, "Minutes")
        val hr = getUnit(UnitCategory.TIME, "Hours")
        val d = getUnit(UnitCategory.TIME, "Days")
        val wk = getUnit(UnitCategory.TIME, "Weeks")
        val yr = getUnit(UnitCategory.TIME, "Julian Years")

        assertEquals(60.0, UnitConverterData.convert(1.0, min, s, UnitCategory.TIME), 1e-6)
        assertEquals(60.0, UnitConverterData.convert(1.0, hr, min, UnitCategory.TIME), 1e-6)
        assertEquals(3600.0, UnitConverterData.convert(1.0, hr, s, UnitCategory.TIME), 1e-6)
        assertEquals(24.0, UnitConverterData.convert(1.0, d, hr, UnitCategory.TIME), 1e-6)
        assertEquals(86400.0, UnitConverterData.convert(1.0, d, s, UnitCategory.TIME), 1e-6)
        assertEquals(7.0, UnitConverterData.convert(1.0, wk, d, UnitCategory.TIME), 1e-6)
        assertEquals(365.25, UnitConverterData.convert(1.0, yr, d, UnitCategory.TIME), 1e-3)
    }

    @Test
    fun testZeroAndNegativeEdgeCases() {
        val m = getUnit(UnitCategory.LENGTH, "Meters")
        val km = getUnit(UnitCategory.LENGTH, "Kilometers")
        assertEquals(0.0, UnitConverterData.convert(0.0, m, km, UnitCategory.LENGTH), 1e-6)

        val deg = getUnit(UnitCategory.ANGLE, "Degrees")
        val rad = getUnit(UnitCategory.ANGLE, "Radians")
        assertEquals(-PI / 2.0, UnitConverterData.convert(-90.0, deg, rad, UnitCategory.ANGLE), 1e-6)

        // Zero in number base
        val zeroRes = UnitConverterData.convertNumberBase("0", "Decimal", "Binary")
        assertTrue(zeroRes.isSuccess)
        assertEquals("0", zeroRes.getOrNull())
    }
}
