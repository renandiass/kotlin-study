class User(val id: Int, val name: String, val address: String)

fun User.validateBeforeSave() {
    //It's possible to create a function inside other function to prevent the duplication of code
    fun validate(value: String,
                 fieldName: String) {
        if(value.isEmpty()) {
            //Inside a local function it's possible to access outer function parameters
            throw IllegalArgumentException("Can't save user $id, empty $fieldName")
        }
    }
    validate(name, "Name")
    validate(address, "Address")
}

//To improve this example we have to implement thins method as extension function of the User class, because we will
//have access to all user properties
fun saveUser(user: User) {
    //It's possible to create a function inside other function to prevent the duplication of code
    fun validate(value: String,
                 fieldName: String) {
        if(value.isEmpty()) {
            //Inside a local function it's possible to access outer function parameters
            throw IllegalArgumentException("Can't save user ${user.id}, empty $fieldName")
        }
    }
    validate(user.name, "Name")
    validate(user.address, "Address")
}

fun main(args: Array<String>) {
    val user = User(id = 1, name = "User Test", address = "Test Street")
    saveUser(user)

    val invalidUserName = User(id = 2, name = "", address = "Test Street")
    try { saveUser(invalidUserName) } catch (e : IllegalArgumentException) { println(e.message) }

    val invalidUserAddress = User(id = 3, name = "User Test", address = "")
    try { invalidUserAddress.validateBeforeSave() } catch (e : IllegalArgumentException) { println(e.message) }
}