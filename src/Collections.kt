// Top level property, it's similar to a constant, static field
val UNIX_FILE_SEPARATOR = "\n"

// Extension function, add method to String class, only in this context, if I want to use this function in other
// files, it's necessary to import this function. Extension function don't have access to private or protected fields
// inside the class, It's not possible to override an extension function, because it's static and java called the function
// in a static way
fun String.lastChar(): Char = get(length - 1)

// Extension property, the content of a String cannot be modified, because of this it's possible to define a property
// as a val, only with getter
val String.last: Char
    get() = get(length - 1)

// The content of a StringBuilder can be modified, because of this it's possible to declare as var
var StringBuilder.lastChar : Char
        get() = get(length-1)
        set(value) = this.setCharAt(length - 1, value)

// varargs it's the same functionality as java, but with one difference in java we use ...
fun varTest(vararg att: String) = println(att.size)


// Infix call, it's possible to call a function without dot, ex: "test" concatVal "123"
infix fun String.concatVal(other: String) = "${toString()} + $other"

fun logAndReturnConcat (address: String, number: String, complement: String): String {
    println("Address: $address, Number: $number, Complement: $complement")
    return "$address, $number, $complement"
}


fun main(args: Array<String>) {
    val set = hashSetOf(1, 7, 53)

    val map = hashMapOf(1 to "one", 2 to "two")

    val builder = StringBuilder("TestBuilder")
    builder.lastChar = '3'

    // Named parameter, make easier to call functions with long parameter list
    val completeAddress = logAndReturnConcat(address = "Test Street", number = "123", complement = "2 floor")

    // Destructuring a Pair, the to infix method create a Pair object for any kind of object, it's a generic
    // expression function
    val (number, name) = 1 to "one"

    println(set.javaClass)
    println(map.javaClass)
    println("Test".lastChar())
    println("StringBuilderValue = $builder, lastChar = ${builder.lastChar}")
    println("Test".last)
    varTest("123", "456")
    println("Test" concatVal "123")
    println(completeAddress)
}