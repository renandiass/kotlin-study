
fun main(args: Array<String>) {
    println("Value: ${numberConversion("2")}")
    println("Value: ${numberConversion("Test")}")

    println("Value: ${numberConversionWithLogging("2")}")
    println("Value: ${numberConversionWithLogging("Test")}")
}

fun numberConversion(strNumber : String): Int? {
    // In kotlin if's / when/ try are expressions, it's possible to return some value
    return try { Integer.parseInt(strNumber) } catch (exp: NumberFormatException) { null }
}

fun numberConversionWithLogging(strNumber : String): Int? {
    return try {
        println("Attempt to convert strNumber: $strNumber")
        // The last line is the expression return
        Integer.parseInt(strNumber)
    } catch (exp: NumberFormatException) {
        println("Exception, message: ${exp.message}")
        null
    }
}
