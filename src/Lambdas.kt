
data class LambdaPerson(val name: String, val age: Int)

fun main(args: Array<String>) {
    val people = listOf(LambdaPerson(name = "John", age = 32), LambdaPerson(name = "Kelly", age = 22))

    println(listOf(1, 2, 3, 4).map { it * 2 }.find { it > 4 })
    println(listOf(1, 2, 3, 4).asSequence().map { it * 2 }.find { it > 4 })

    //Do the same
    println(people.maxBy { it.age })
    println(people.maxBy(LambdaPerson::age))
    println(people.maxBy {p: LambdaPerson -> p.age})
}