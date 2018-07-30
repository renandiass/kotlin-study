class PersonTest(val firstName: String, val lastName: String) {
    override fun equals(o: Any?): Boolean {
        // Safe casts as? to perform a safe conversion
        val otherPerson = o as? PersonTest ?: return false

        return otherPerson.firstName == firstName &&
                otherPerson.lastName == lastName
    }

    override fun hashCode(): Int {
        return firstName.hashCode() * 37 + lastName.hashCode()
    }
}

// All regular types are non-null by default, to accept null it's necessary to use
// ? (question mark) after any type
//fun strLenSafe(s: String?) = s.length it's not possible to do this call or to
// pass null variable to a not null variable, both cases will result in a compile error
// To work it's necessary to check whether it's different from null or not
fun strLenSafe(s: String?) = if(s != null) s.length else 0

// In this case if s is null this code will return null
fun strLenSafeUsingSafeCallOperator(s: String?) = s?.length

// Using "Elvis operator" it's possible to return a default value when the variable was null
fun strLenSafeUsingSafeCallOperatorAndElvisOperator(s: String?) = s?.length ?: 0

// Elvis operator throwing exception
fun strLenSafeUsingSafeCallOperatorAndElvisOperatorThrowingException(s: String?) = s?.length ?: throw IllegalArgumentException("Nullable Variable")

// Not null assertion, converts any value to a non null type, for instance !!foo, if foo != null = foo, if foo == null
// throws a NullPointerException
fun ignoreNulls(s: String?) {
    val sNotNull: String = s!!
    println(sNotNull.length)
}

// The let function makes it easier to deal with nullable expression, this function receives a non null String, so
// it's not possible to call this function passing a nullable type val email: String? = ..... and sendEmailTo(email)
// this code throw a type mismatch Error
fun sendEmailTo(email: String) {
    println("Send email to $email")
}

// Extension function for a nullable type, it means you can call this function on nullable values
fun String?.checkIsNullOrBlank(): Boolean = this == null || this.isBlank()

// To deal with null it's possible to create nullable receivers
fun verifyUserInput(s: String?) {
    // No safe call is needed
    if(s.isNullOrBlank()) {
        println("Please fill with a non null variable")
    }
}

class MyTest {
    // In kotlin it's not possible to create a non null property and not initialize it in initialization time
    // private var test: String
    // To do that you have to use late initialization, but the property should be a var, val it's compile as
    // final attributes and should be initialized inside the constructor
    private lateinit var test: String

    fun setUp() {
        test = "Test"
    }
}

// In kotlin it's possible to chain a lot of safe call operations, for instance:
// person?.address?.street

fun main(args: Array<String>) {
    println(strLenSafe(null))
    println(strLenSafeUsingSafeCallOperator(null))
    println(strLenSafeUsingSafeCallOperatorAndElvisOperator(null))
    //println(strLenSafeUsingSafeCallOperatorAndElvisOperatorThrowingException(null))
    println(ignoreNulls("Test"))
    //println(ignoreNulls(null))
    var email: String? = "Test"
    email?.let { sendEmailTo(it) }
    email = null
    email?.let { sendEmailTo(it) }
}

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
