import java.awt.Window
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.io.File
import java.io.Serializable

//Kotlin declarations are public and final by default, nested classes are't inner by default , they don't contain
//an implicit reference to their outer class

// Defining an interface
interface Clickable {
    //Abstract property declaration
    val clickFunctionName: String
    fun click()
    //Unlike java 8 it's not necessary to put a default modifier when you implement a default method inside an interface
    fun showOff() = println("I'm clickable")
}

//Every member in an interface is open by default, even default functions
interface Focusable {
    fun setFocus(b: Boolean) = println("I ${if(b) "got" else "lost"} focus.")

    fun showOff() = println("I'm focusable")
}

//By default classes in kotlin are final and it's not possible to inherit it, if you want it's necessary to use the
//open modifier
open class RichButton(override val clickFunctionName: String): Clickable {
    //final by default, you can't override it in a subclass
    fun disable() {}
    //you may override it in a subclass, because the open modifier
    open fun animate() {}
    //you may override it in a subclass, because you override an open function and is open as well, if you want to
    //prevent subclasses to override this method, it's necessary to use final modifier explicitly
    override fun click() {
        println("Rick button was clicked")
    }
}

abstract class Animated {
    abstract fun animate()

    //Non abstract function aren't open by default, they are final by default, only abstract functions are open by default
    open fun stopAnimating() {}

    fun animateTwice() {}
}

//kotlin modifiers, public is the default modifier, in kotlin we don't have package default modifier, we have public
//protected that is visible only in the same class or subclasses, not visible in same package and internal which
//means visible inside a module that means a set of files compile together

internal open class TalkativeButton : Focusable {
    private fun yell() = println("Hey!")
    protected fun whisper() = println("Let's talk!")
}

//It's not possible to create an extension function increasing the modifier, it's necessary to use internal
//fun TalkativeButton.giveSpeech() {
//It's not possible to access private function
//    yell()
//It's not possible to access protected function
//    whisper()
//}

interface State : Serializable

interface View {
    fun getCurrentState(): State
    fun restoreState(state: State) {}
    //It's analogue of a static nested class in java, to turn it into an inner class to contain reference to
    //outer class it's necessary to use inner modifier
    class ButtonState: State
}

class Button(val html : String): Clickable, Focusable {
    override val clickFunctionName: String
        get() = html.substringAfter("onclick=")
    //It's necessary to implement showOff because it's not possible to know what default method use
    override fun showOff() {
        //To call super methods
        super<Clickable>.showOff()
        super<Focusable>.showOff()
    }

    //Unlike java the override modifier is mandatory
    override fun click() {
        println("I was clicked")
    }
}

open class UserTest(val nickname: String)
//Calling superclass constructor
class TwitterUser(nickname: String) : UserTest(nickname)
//Class with private constructor
class Secretive private constructor()

class PersonTest constructor(_nickname : String) {
    val nickname: String
    //init block
    init {
        nickname = _nickname
    }
}

//Accessing backing field in a setter
class People(private val name : String) {
    var city : String = "unspecified"
    set(value) {
        println("""
            City was changed for $name,
            $field -> $value.""".trimIndent())
        field = value
    }

    //It's not possible to change this property outside the class
    var counter: Int = 0
        private set

    fun addYear(years : Int) {
        counter += years
    }
}

// If we use the data modifier it generates equals, hashcode, toString and copy automatically
data class Group(val id: Int, val name: String)

// Decorator pattern: The essence of this pattern is that a new class is created, implementing the same interface
// as the original class and storing the instance of the original class as a field
// If you use by the class will automatically implement all methods using the original class, similar with an wrapper
class CountingSet<T>(private val innerList: MutableCollection<T> = HashSet()) : MutableCollection<T> by innerList {
    var objectsAdded = 0

    override fun add(element: T): Boolean {
        objectsAdded++
        return innerList.add(element)
    }

    override fun addAll(elements: Collection<T>): Boolean {
        objectsAdded += elements.size
        return innerList.addAll(elements)
    }
}

// Use object modifier to create singleton objects
object CaseInsensitiveFileComparator : Comparator<File> {
    override fun compare(o1: File, o2: File): Int {
        return o1.path.compareTo(o2.path, ignoreCase = true)
    }
}

//Companion objects: a place for factory methods and static members
//companion objects are a good place to call private constructors It has access to all private members of the class
//and it's an ideal candidate to implement the factory pattern, a companion object can implement interfaces
class A private constructor(val nickname: String) {
    companion object {
        fun facebookNick(facebookAccount: String): A {
            println("companion object called")
            return A(nickname = "Facebook: $facebookAccount")
        }
        fun googleNick(gmail: String): A {
            println("companion object called")
            return A(nickname = "Gmail: $gmail")
        }
    }
}

interface JsonFactory<T> {
    fun fromJson(jsonText : String): T
}

class Street(val street : String) {
    companion object : JsonFactory<Street>{
        override fun fromJson(jsonText: String): Street {
            return Street(jsonText.substringAfter(delimiter = ",").replace("\"", ""))
        }
    }
}

fun <T> loadFromJson(factory: JsonFactory<T>) : T {
    return factory.fromJson("\"Street\":\"Test Street\"")
}

// Object expression, anonymous inner classes
// - Unlike java kotlin anonymous inner classes can implement more than one interface or no interfaces
// - Unlike object declarations, anonymous objects aren't singleton, every time this expression are executed a new
// instance is created
// - Unlike java kotlin anonymous inner classes can access any variable, not only final
fun countClicks(window: Window) {
    var clickCount = 0

    window.addMouseListener(object : MouseAdapter() {
        override fun mouseClicked(e: MouseEvent?) {
            clickCount++
        }
    })
}

fun main(args: Array<String>) {
    val button = Button("onclick=testFunction")
    button.showOff()
    button.setFocus(true)
    button.click()
    val people = People("Joe")
    people.city = "Berlin"

    val group2 = Group(id = 2, name = "Group 2")
    val copyOfGroup2 = Group(id = 2, name = "Group 2")

    // == is the default way to compare objects in kotlin, it calls equals under the hood, if you want to compare
    // references you can use ===
    println("group2 == copyOfGroup2 : ${group2 == copyOfGroup2}")
    println("group2 === copyOfGroup2 : ${group2 === copyOfGroup2}")
    val referenceOfGroup2: Group = group2
    println("group2 === referenceOfGroup2 : ${group2 === referenceOfGroup2}")

    // Create a new object based on group object and change the name
    val copy: Group = group2.copy(name = "Group 3")
    println(copy)

    val countable = CountingSet<Int>()
    countable.addAll(listOf(1, 1, 2))
    println("${countable.objectsAdded} objects were added, but ${countable.size} remains")

    println(CaseInsensitiveFileComparator.compare(File("/Opt"), File("/opt")))

    println(A.facebookNick(facebookAccount = "renandiass").nickname)
    println(A.googleNick("renandiass@gmail.com").nickname)

    println(loadFromJson(Street).street)
}

//Print Result
//I'm clickable
//I'm focusable
//I got focus.
//I was clicked
//City was changed for Joe,
//unspecified -> Berlin.
//group2 == copyOfGroup2 : true
//group2 === copyOfGroup2 : false
//group2 === referenceOfGroup2 : true
//Group(id=2, name=Group 3)
//3 objects were added, but 2 remains
//0
//companion object called
//Facebook: renandiass
//companion object called
//Gmail: renandiass@gmail.com
//Street:Test Street
