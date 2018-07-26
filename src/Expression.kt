interface Expr
class Num(val value:Int): Expr
class Sum(val left:Expr, val right:Expr): Expr

//When we mark a superclass with sealed modifier we restrict the possibility of creating subclasses, all
//direct subclasses must be nested
sealed class SealedExpr {
    class Num2(val value: Int) : SealedExpr()
    class Sum2(val left: SealedExpr, val right: SealedExpr) : SealedExpr()
}

//When we use sealed classes it's not necessary to create an else entry inside when expression and if you add
//a new class inside the sealed superclass it's necessary to create another entry in this when if you don't do that
//a compile error will occur
fun evalSealed(e: SealedExpr): Int =
        when(e) {
            is SealedExpr.Num2 -> e.value
            is SealedExpr.Sum2 -> evalSealed(e.right) + evalSealed(e.left)
        }

fun eval(e: Expr): Int =
        when(e) {
        // If we check the variable for a certain type, it's not necessary to cast, implicit cast
        // val n = e as Num -> explicit cast
            is Num ->
                    e.value // expression body, only one value
            is Sum ->
                    eval(e.right) + eval(e.left)
            else ->
                    throw IllegalArgumentException("Unknown expression") // Not necessary to use new
        }

fun evalWithLogging(e: Expr): Int =
        when(e) {
        // If we check the variable for a certain type, it's not necessary to cast, implicit cast
        // val n = e as Num -> explicit cast
            is Num -> {
                println("num: ${e.value}")
                e.value // block body, the last value of the expression will be returned
            }
            is Sum -> {
                val left = evalWithLogging(e.left)
                val right = evalWithLogging(e.right)
                println("Sum: $left + $right")
                left + right
            }
            else ->
                throw IllegalArgumentException("Unknown expression")
        }

fun main(args: Array<String>) {
    println(eval(Sum(Num(1), Num(2))))
    println(evalSealed(SealedExpr.Sum2(SealedExpr.Num2(1), SealedExpr.Num2(2))))
    println(evalWithLogging(Sum(Num(4), Num(2))))
}