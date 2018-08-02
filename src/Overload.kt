import java.math.BigDecimal
import java.time.LocalDate
import kotlin.comparisons.compareValuesBy

data class Rectangle(val upperLeft: Point, val lowerRight: Point)

// Implementing the in convention
operator fun Rectangle.contains(p: Point): Boolean {
    // Until creates an open range, that doesn't include the end point
    return p.x in upperLeft.x until lowerRight.x &&
            p.y in upperLeft.y until lowerRight.y
}

class Point2(val x: Int, val y: Int) {
    // We can't define an infinite number of such componentN, a simple way to return multiple values
    // from a function is to use the Pair and Triple classes from the standard library
    operator fun component1() = y
    operator fun component2() = x
}

data class Point(val x: Int, val y: Int): Comparable<Point> {
    // When we use data modifier this methods are overloaded, so it's not possible to overload again
    //operator fun component1() = y
    //operator fun component2() = x

    // Defines an overload for operator +
    operator fun plus(other: Point) : Point {
        return Point(x + other.x, y + other.y)
    }

    // Defines an operator with different operand types
    operator fun times(scale: Double) : Point {
        return Point((x * scale).toInt(), (y * scale).toInt())
    }

    // It's possible to overload the function iterator(), to create an
    // iterator and iterate using for

    // Implementing get convention to make possible the use of p[0], p[1]
    operator fun get(index: Int): Int {
        return when(index) {
            0 -> x
            1 -> y
            else ->
                    throw IndexOutOfBoundsException("Invalid coordinate $index")
        }
    }

    override fun compareTo(other: Point): Int {
        // Compare values in order, first x, after y
        return compareValuesBy(this, other, Point::x, Point::y)
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

    val destructPoint = Point2(x = 20, y = 30)
    val(y1,x1) = destructPoint
    println("Inverting destructuring data, x=$x1, y=$y1")

    // Destructuring, this declarations are transformed into componentN function calls
    val(x,y) = p1
    println("x = $x, y = $y")

    // Using the in convention to check whether a point belongs to a rectangle or not
    val rect = Rectangle(Point(10, 20), Point(50, 50))
    println(Point(20, 30) in rect)

    // Working with ranges the range .. is converted to rangeTo function that Comparable interface offers
    val now = LocalDate.now()
    val vacation = now..now.plusDays(10)
    println(now.plusWeeks(1) in vacation)

    // This comparison is transformed into p1.compareTo(p2) > 0
    println("p1(x=${p1[0]}, y=${p1[1]}) is greater than p2(x=${p2[0]}, y=${p2[1]}) ? answer: ${p1 > p2}")

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