class Person(val name: String, private val isMarried: Boolean = true) {
    // A property with a custom getter or a function without parameter ? Both are similar, there is no difference in
    // implementation or performance, only in readability
    val hasSon: Boolean
        get() = name == "Renan" && isMarried

}


class Utils {

    // Val is immutable, corresponds to a final variable in java, if we initialize it, it's not necessary to indicate
    // the type, the implicit inference
    val pi = 3.14

    // Var is mutable it's possible to change the value, but not the type
    var pi2 = 3.0

    // With expression body it's possible to omit the return statement, with block bodies, it's necessary to use the
    // return statement
    fun max(a: Int, b: Int): Int = if (a > b) a else b

    // String template $a, the compiled code creates a StringBuilder and appends the constant parts and variable
    // values to it
    fun printVal(a: String) = println("This variable is $a")
    fun printPerson(a: Array<String>) = println("Hello ${if (a.isNotEmpty()) a[0] else "someone"}!")

    fun getWarmth(color:Color) = when(color) {
        Color.RED, Color.ORANGE -> "warm"
        Color.GREEN -> "neutral"
    }
}

//enum
enum class Color(val r:Int, val g:Int, val b:Int){
    RED(255,0,0), ORANGE(255, 165, 0), GREEN(0, 255, 0)
}

fun main(args : Array<String>) {

    // If we don't initialize the val, it's necessary to indicate the type
    val answer: Int
    answer = 42

    Utils().printVal(answer.toString())

    Utils().printPerson(arrayOf("Renan"))

    println(Utils().max(1, 2))

    val renan = Person("Renan")
    println("Nome: ${renan.name}, hasSon: ${renan.hasSon}")

    val otherPerson = Person("John", false)
    println("Nome: ${otherPerson.name}, hasSon: ${otherPerson.hasSon}")

    print("Color ${Color.RED} is ${Utils().getWarmth(Color.RED)}")
}