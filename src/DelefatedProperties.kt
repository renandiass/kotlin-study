import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

/**
 * Created by renan on 8/2/18.
 */
// General syntax
//class Foo {
//    var p: Type by Delegate()
//}

open class PropertyChangeAware {
    protected val changeSupport = PropertyChangeSupport(this)
    fun addPropertyChangeListener(listener: PropertyChangeListener) {
        changeSupport.addPropertyChangeListener(listener)
    }
    fun removePropertyChangeListener(listener: PropertyChangeListener) {
        changeSupport.removePropertyChangeListener(listener)
    }
}

class LazyPerson(val name: String, age: Int, salary: Int): PropertyChangeAware() {
    // The lazy function is thread safe by default
    val emails by lazy { loadEmails() }

    private val observer = {
        prop: KProperty<*>, oldValue: Int, newValue: Int ->
            changeSupport.firePropertyChange(prop.name, oldValue, newValue)
    }

    var age: Int by Delegates.observable(age, observer)
    var salary: Int by Delegates.observable(salary, observer)

    private fun loadEmails(): List<String> {
        println("Load emails")
        return listOf("test@test.com", "test2@test.com")
    }
}

fun main(args: Array<String>) {
    val lazyPerson = LazyPerson("Joe", 25, 8000)
    lazyPerson.addPropertyChangeListener(PropertyChangeListener { event ->
        println("Property ${event.propertyName} changed from ${event.oldValue} to ${event.newValue}")
    })
    lazyPerson.age = 30
    println(lazyPerson.name)
    println(lazyPerson.emails)
    lazyPerson.salary = 8500
}