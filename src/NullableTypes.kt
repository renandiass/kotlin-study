// Any type, include a nullable type, can be substituted for a type parameter, so you should
// use safe char ?
fun <T> printHashCode(t: T) {
    println(t?.hashCode())
}

// To make it non null, it's necessary to specify a non null upper bound for it
fun <T: Any> printNonNullHashCode(t: T) {
    println(t.hashCode())
}

// When we use a non null Int for instance it will be converted to a primitive int, but whe we use a nullable Int
// it's not possible to convert to a primitive int, because primitive types cannot store null values, so it will be
// converted to the wrapper
fun testPrimitiveTypes(primitiveInt: Int, nonPrimitiveType: Int?) {
    println(primitiveInt)
    println(nonPrimitiveType)
}

fun nmbConversion() {
    val i = 1
    // It's not possible to perform this conversion, we have to do it using toLong method
    //val l: Long = i
    val l: Long = i.toLong()

    println(l)
    // Convert String to Int
    println("42".toInt())
    // it will throw a NumberFormatException
    // println("test".toInt())
}

fun anyType(): Any {
    // Any is similar to object, it is the supertype of all non-nullable types in kotlin
    // Assert a primitive type to a variable of type any perform automatic boxing to Integer
    val answer: Any = 42
    return answer
}

// The unit type in kotlin fulfills the same function as void in java, it's the same to write this function as
// fun unitType(): Unit {
fun unitType() {
}
interface Processor<T> {
    fun process(): T
}

// Unit is useful to when we override a function that returns a generic type and make it return a value of the Unit type
class NoResultProcessor : Processor<Unit> {
    override fun process() {
        // do something
    }
}

// Collection and platform types, when we receive a collection from java we can decide if this collection will
// be mutable or immutable and non nullable or nullable in kotlin
fun collectionsFun(coll1: Collection<Int>, coll2: MutableCollection<Int>) {
    println(coll1)
    coll2.add(6)
    println(coll2)
}

// The nothing type, this function never returns, it never complete successfully
fun fail(message: String): Nothing {
    throw IllegalStateException(message)
}

// Collection that can be null List<String>?
fun collectionOfNullableValues(listOfInteg: List<String>?) : List<Int?>{
    // Collection of nullable values
    val result = ArrayList<Int?>()
    for (one in listOfInteg!!) {
        try {
            result.add(one.toInt())
        } catch (e: NumberFormatException) {
            result.add(null)
        }
        // It's possible to improve this code using
        result.add(one.toIntOrNull())
    }
    return result
}

// When we have to use java code and this code has some information about nullability as @Nullable or other annotations
// kotlin translate it to adequate type, for instance
//      Java           Kotlin
//@Nullable + Type =    Type?
//@NotNull  + Type =    Type
//      Type       =  Type? or Type
// If we don't have this information in java code, we can work with the Type as null or non null
// The value checking is performed when the function is called, not when the parameter is used
// When we implement some java interface, we can define if we will use null or non null types

fun main(args: Array<String>) {
    printHashCode(null)
    printNonNullHashCode("Test")
    testPrimitiveTypes(5, null)
    nmbConversion()
    println(anyType())

    val p = Person("Test", true)
    val name = p.name ?: fail("No name")
    println(name)

    println(collectionOfNullableValues(listOf("2", "3", "Test")))

    val numbers = collectionOfNullableValues(listOf("2", "3", "Test"))

    //filter non nullable
    println(numbers.filterNotNull())

    // Immutable snd Mutable lists
    //  Immutable  Mutable
    //   listOf   mutableListOf/arrayListOf
    //   setOf    mutableSetOf/hashSetOf/linkedSetOf/sortedSetOf
    //   mapOf    mutableMapOf/hashMapOf/linkedMapOf/sortedMapOf
    collectionsFun(listOf(1), mutableListOf(1))

    // Arrays of primitives in kotlin, we have one for each type intArrayOf..floatArrayOf...
    val arr1 = intArrayOf(1, 2, 3)
    val arr2 = arrayOfNulls<Int>(10)
    val arr3 = Array(26) {i -> ('a' + i).toString()}

    // Passing a collection to a vararg argument
    val strings = listOf("a", "b", "c")
    // The spread operator *, is used to pass an array when vararg parameter is expected
    println("%s %s %s".format(*strings.toTypedArray()))
    // To covert an array of boxed Integers to an array of primitive types, it's possible to use toIntArray

    // forEachIndexed with an array
    arr1.forEachIndexed {index, element -> println("Argument $index is $element")}
}