package uk.co.stegriff.hellojavalin

import io.javalin.Javalin
import kotlin.io.encoding.Base64

data class Response(val status: String, val message: String, val links: List<String>)

val ok = { message: String -> Response("OK", message, listOf()) }
val links = { message: String, links: List<String> -> Response("OK", message, links) }
val greet = { name: String -> "Hello $name!" }
fun String.toIntOrZero(): Int {
    return this.toIntOrNull() ?: 0
}

val reverse = { input: String -> input.reversed() }
val add = { a: String, b: String -> "${a.toIntOrZero() + b.toIntOrZero()}" }
val b64encode = { input: String -> Base64.encode(input.encodeToByteArray()) }
val b64decode = { input: String -> Base64.decode(input).decodeToString() }

fun main() {
    val app = Javalin.create().start(7070)
    val menu = links(
        "Hello from Javalin + Kotlin + SteGriff",
        listOf(
            "/about",
            "/greet/{name}",
            "/add/{a}/{b}",
            "/reverse/{string}",
            "/b64encode/{string}",
            "/b64decode/{string}"
        )
    )
    val about = links(
        "By SteGriff. This is my first Javalin API, and first hosted Kotlin project. "+
        "It was a chance to practice some Kotlin function syntax, as well as working " +
        "in the IntelliJ IDEA",
        listOf(
            "/",
            "https://github.com/stegriff/hello-javalin",
            "https://stegriff.co.uk/"
        )
    )

    app.get("/") { c : io.javalin.http.Context -> c.json(menu) }
    app.get("/about") { c : io.javalin.http.Context -> c.json(about) }
    app.get("/greet/{name}") { c -> c.json(ok(greet(c.pathParam("name")))) }
    app.get("/reverse/{input}") { c -> c.json(ok(reverse(c.pathParam("input")))) }

    app.get("/b64encode/{input}") { c -> c.json(ok(b64encode(c.pathParam("input")))) }
    app.get("/b64decode/{input}") { c -> c.json(ok(b64decode(c.pathParam("input")))) }

    app.get("/add/{a}/{b}") { c -> c.json(ok(add(c.pathParam("a"), c.pathParam("b")))) }
}