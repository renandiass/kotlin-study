import java.util.*

fun main(args: Array<String>) {
    println("Test")

    //Range
    val oneToTen = 1..10

    // Iterate from 2 to 2 until value 1
    for(i in 100 downTo 1 step 2) {
        println(i)
    }

    //Check
    whileLops()

    val binaryReps = TreeMap<Char, String>()

    //Letters range
    for(c in 'A'..'F') {
        val binary = Integer.toBinaryString(c.toInt())
        binaryReps[c] = binary
    }

    //Unpack a map into 2 variables - key/value
    for((letter, binary) in binaryReps) {
        println("$letter - $binary")
    }

    // It's possible to use the same approach used with maps, it's not necessary to store the index into a variable
    val list = arrayOf("10", "11", "1001")
    for((index, element) in list.withIndex()) {
        println("$index - $element")
    }


    println("Value c is letter ? - ${isLetter('c')}")
}

fun whileLops() {
    var i = 0

    while(i < 10) {
        println("i: $i")
        i++
    }

    do {
        println("i: $i")
        i--
    } while(i >= 0)
}

//in is similar with contains
fun isLetter(c: Char): Boolean = c in 'a'..'z' || c in 'A'..'Z'