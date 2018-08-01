import java.math.BigDecimal

data class Point(val x: Int, val y: Int) {
    // Defines an overload for operator +
    operator fun plus(other: Point) : Point {
        return Point(x + other.x, y + other.y)
    }

    // Defines an operator with different operand types
    operator fun times(scale: Double) : Point {
        return Point((x * scale).toInt(), (y * scale).toInt())
    }
}

// Defines an overload in an extension function for operator +
operator fun BigDecimal.plus(other: BigDecimal) = this.add(other)
// Defines an overload in an extension function for operator -
operator fun BigDecimal.minus(other: BigDecimal) = this.subtract(other)
// Defines an overload in an extension function for operator *
operator fun BigDecimal.times(other: BigDecimal) = this.multiply(other)
// Defines an overload in an extension function for operator /
operator fun BigDecimal.div(other: BigDecimal) = this.divide(other)
// Defines an overload in an extension function for operator %
operator fun BigDecimal.mod(other: BigDecimal) = this.divide(other)
// Defines an overload in an extension function for operator +b
operator fun BigDecimal.unaryPlus() = this.add(BigDecimal(1))
// Defines an overload in an extension function for operator -b
operator fun BigDecimal.unaryMinus() = this.add(BigDecimal(1))
// Defines an overload in an extension function for operator !b
operator fun BigDecimal.not() = this.add(BigDecimal(1))
// Defines an overload in an extension function for operator ++b, b++
operator fun BigDecimal.inc() = this.add(BigDecimal(1))
// Defines an overload in an extension function for operator --b, b--
operator fun BigDecimal.dec() = this.add(BigDecimal(1))

// to perform += for instance it's possible to create a operator function called plusAssign, minusAssign...
// but only create these methods if your class is mutable, if your class is immutable create only
// plus, minus, etc... it's possible to use +=, -+ with these methods in immutable classes
// the result will be a new object

// Kotlin bitwise operators
// shl - signed shift left
// shr - signed shift right
// ushr - unsigned shift right
// and - Bitwise and
// or - Bitwise or
// xor - Bitwise xor
// inv - Bitwise inversion

fun main(args: Array<String>) {
    val p1 = Point(10, 20)
    val p2 = Point(10, 40)

    val b1 = BigDecimal("10")
    val b2 = BigDecimal("5")

    // an equality check == is transformed into an equals call and a null check
    // b2?.equals(b1) ?= (b1 == null)
    println(b2 == b1)

    // Bitwise operations
    println(0x0F or 0xF0)
    println(0x1 shl 4)

    println(p1 + p2)
    // Defines an operator with different type, but kotlin operators don't automatically support
    // commutativity, if you want to invert the operation to 2.0 * p1 you will have to overload the
    // Double.times...
    println(p1 * 2.0)
    println(b1 + b2)
    println(b1 / b2)
}