package uk.co.stegriff.hellojavalin

import io.javalin.Javalin
import kotlin.io.encoding.Base64

data class Response(
  val status: String,
  val message: String, 
  val links: List<String>
)

// Global config
val config = ConfigLoader.load()
    
// Base response builders
val ok = { message: String, -> links(message, listOf()) }
val links = { 
  message : String, 
  links: List<String> -> 
  Response("OK", message, hostify(links))
}

// Enrichers
val hostify = { links: List<String> -> links.map { 
    if (it.startsWith("/")) config.host + it 
    else it
  }
}

// Functional builders
val greet = { name: String -> "Hello $name!" }
val reverse = { input: String -> input.reversed() }
val add = { a: String, b: String -> "${a.toIntOrZero() + b.toIntOrZero()}" }
val b64encode = { input: String -> Base64.encode(input.encodeToByteArray()) }
val b64decode = { input: String -> Base64.decode(input).decodeToString() }

// Extensions
fun String.toIntOrZero(): Int {
    return this.toIntOrNull() ?: 0
}

fun main() {
    val app = Javalin.create().start(config.port)
    val menuText = greet("from Javalin + Kotlin + SteGriff");
    val menuLinks = listOf(
        "/about",
        "/greet/{name}",
        "/add/{a}/{b}",
        "/reverse/{string}",
        "/b64encode/{string}",
        "/b64decode/{string}"
    )
        
    val aboutLinks = listOf(
        "/",
        "https://github.com/stegriff/hello-javalin",
        "https://stegriff.co.uk/"
    )
        
    val aboutText = 
        "By SteGriff. This is my first Javalin API, and first hosted Kotlin project. " +
        "It was a chance to practice some Kotlin function syntax, as well as working " +
        "in the IntelliJ IDEA"

    app.get("/") { c -> c.json(links(menuText, menuLinks)) }
    app.get("/about") { c -> c.json(links(aboutText, aboutLinks)) }
    app.get("/greet/{name}") { c -> c.json(ok(greet(c.pathParam("name")))) }
    app.get("/reverse/{input}") { c -> c.json(ok(reverse(c.pathParam("input")))) }

    app.get("/b64encode/{input}") { c -> c.json(ok(b64encode(c.pathParam("input")))) }
    app.get("/b64decode/{input}") { c -> c.json(ok(b64decode(c.pathParam("input")))) }

    app.get("/add/{a}/{b}") { c -> c.json(ok(add(c.pathParam("a"), c.pathParam("b")))) }
    
}