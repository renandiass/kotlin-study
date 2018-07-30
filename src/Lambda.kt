/**
 * Created by renan on 7/17/18.
 */
data class Person(val name:String, val age: Int)

fun salute() = println("Salute !!!")

fun alphabet(): String {
    val result = StringBuilder()
    for(letter in 'A'..'Z') {
        result.append(letter)
    }
    result.append("\n I know the alphabet !!!")
    return result.toString()
}

// Rewrite the alphabet function using with, with receives a 'receiver' and a lambda , the with converts the first
// argument into a receiver of the lambda, that's passed as a second argument so we can use this, that refers to the
// receiver or we can omit that
// An extension function is, in a sense, a function with a receiver
fun alphabetUsingWith(): String {
    val result = StringBuilder()
    return with(result) {
        for(letter in 'A'..'Z') {
            this.append(letter)
        }
        this.append("\n I know the alphabet !!!")
        return this.toString()
    }
}
// We can omit this clause
fun alphabetUsingWithAndExpressionBody() = with(StringBuilder()) {
    for(letter in 'A'..'Z') {
        append(letter)
    }
    append("\n I know the alphabet !!!")
    toString()
}

// The apply function works almost exactly the same as with, the only difference is that apply always returns the
// object passed to it as an argument(the receiver object)
fun alphabetUsingApply() = StringBuilder().apply {
    for(letter in 'A'..'Z') {
        append(letter)
    }
    append("\n I know the alphabet !!!")
}.toString()

// We can simplify more using some other functions that work in the same way, with receivers, this function for instance
// creates a StringBuilder and call toString
fun alphabetUsingBuildString() = buildString {
    for(letter in 'A'..'Z') {
        append(letter)
    }
    append("\n I know the alphabet !!!")
}

// SAM constructor: explicit conversion of lambdas to functional interfaces
fun createAllDoneRunnable(): Runnable {
    // The SAM constructor takes a single argument, a lambda that will be used as the body of the single abstract
    // method in the functional interface
    return Runnable {println("All done!")}
}

fun printMessagesWithPrefix(messages: Collection<String>, prefix: String) {
    // Unlike java in kotlin is possible to access not only final variables, but to modify then,
    // in java is possible to capture a mutable variable only with pogs, creating a final array for instance
    var errorCount = 0
    messages.forEach {
        println("$prefix $it")
        errorCount++
    }
    println("Quantity of errors: $errorCount")
}

fun main(args: Array<String>) {
    val people = listOf(Person("Alice", 29), Person("Bob", 32), Person("Carol", 29), Person("George", 44), Person("Alfred", 32))

    // It's possible to store a lambda in a variable
    val getAge = {p: Person -> p.age}
    // To simplify the lambda it's possible to use the syntax of member reference
    val getAgeMemberRefernce = Person::age

    // in this case I'm calling a top level function and I can omit the name of the class
    run(::salute)

    // It's possible to store a constructor reference inside a variable
    val createPerson = ::Person
    val p = createPerson("George", 40)

    // it's possible to create a function based on references, it's called bound references
    //val georgeAgeFunction = p::age

    // lambda can have more than one instruction, in this case the last expression is the result
    val sum = {x: Int, y: Int ->
        println("computing the sum of $x and $y")
        x + y
    }

    val errors = listOf("404", "403")

    printMessagesWithPrefix(errors, "Error:")
    println(people.maxBy(getAge))

    // create a map grouping by age
    println(people.groupBy { it.age })

    // Evaluates the list eagerly, the intermediate result of each step is stored in a temporary list
    println(people.map(Person::name).filter { it.startsWith("A") })

    // In this example, no intermediate collections will be created, similar with streams in java,
    // for sequences all operations are applied to each element sequentially, the first element is processed(
    // mapped, then filtered), then the second element, and so on
    println(people.asSequence().map(Person::name).filter { it.startsWith("A") }.toList())

    // Eager / map = Collection (1, 4, 9, 18) -> filter (not 1), (yes 4), return 4
    println(listOf(1, 2, 3, 4).map { it * it }.find { it > 3 })
    // Lazy / map = 1 -> filter (not 1) / map = 4 -> (yes 4), return 4
    println(listOf(1, 2, 3, 4).asSequence().map { it * it }.find { it > 3 })

    // in Kotlin it's possible to invoke and pass a lambda as argument for any java method that expects a functional
    // interface, for instance void postponeComputation(int delay, Runnable computation)
    // postponeComputation(1000) { println(42) }, the compiler will convert this lambda into an instance of Runnable
    // if a lambda doesn't access any variables from the function where it's defined, the corresponding anonymous
    // class instance is reused between calls
}
