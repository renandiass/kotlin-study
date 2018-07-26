fun parsePathWithSubstring(path: String) {
    val directory = path.substringBeforeLast("/")
    val fullName = path.substringAfterLast("/")
    val fileName = fullName.substringBeforeLast(".")
    val extension = fullName.substringAfterLast(".")

    println("Dir: $directory, name: $fileName, extension: $extension")
}

fun parsePathWithRegex(path: String) {
    //In a triple quoted string is not necessary to escape any characters, including the backslash, so you can encode
    //the dot with \. rather than \\.
    val regex = """(.+)/(.+)\.(.+)""".toRegex()
    val matchResult = regex.matchEntire(path)
    if(matchResult != null) {
        //Destructing a match expression into three variables
        val(directory, fileName, extension) = matchResult.destructured
        println("Dir: $directory, name: $fileName, extension: $extension")
    }
}

fun multilineTripleQuotedStrings() {
    val kotlinLogo = """| //
                       .|//
                       .|/ \"""
    println(kotlinLogo.trimMargin("."))
}

fun main(args: Array<String>) {
    parsePathWithSubstring("/users/yole/kotlin-book/chapter.pdf")
    parsePathWithRegex("/users/yole/kotlin-book/chapter.pdf")
    multilineTripleQuotedStrings()
}