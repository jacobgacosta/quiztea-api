package server

import jakarta.ws.rs.core.UriBuilder
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory
import jakarta.ws.rs.container.ContainerRequestContext
import jakarta.ws.rs.container.ContainerResponseContext
import jakarta.ws.rs.container.ContainerResponseFilter
import jakarta.ws.rs.ext.Provider
import org.glassfish.jersey.server.ResourceConfig
import java.net.URI
import java.util.logging.Logger

const val BASE_URL = "http://localhost"
const val PORT = 8080

val logger: Logger = Logger.getLogger("ServerLogger")

class Setup : ResourceConfig() {
    init {
        packages("dev.koupper.controllers")
        register(CorsFilter())
    }
}

@Provider
class CorsFilter : ContainerResponseFilter {
    override fun filter(requestContext: ContainerRequestContext, responseContext: ContainerResponseContext) {
        // Permitir cualquier origen, puedes cambiar '*' por tu dominio si lo deseas
        responseContext.headers.add("Access-Control-Allow-Origin", "*")

        // Permitir los encabezados comunes
        responseContext.headers.add("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization")

        // Métodos permitidos
        responseContext.headers.add("Access-Control-Allow-Methods", "POST, OPTIONS")

        // Soporte para cookies si se necesita
        responseContext.headers.add("Access-Control-Allow-Credentials", "true")

        // Responder a las solicitudes OPTIONS (Preflight)
        if (requestContext.method == "OPTIONS") {
            responseContext.status = 200
            responseContext.entity = null  // No enviar cuerpo en la respuesta OPTIONS
        }
    }
}

fun main() {
    val url: URI = UriBuilder.fromUri(BASE_URL)
        .port(PORT)
        .build()

    logger.info("Starting server at $url")

    try {
        val httpServer = GrizzlyHttpServerFactory.createHttpServer(
            url,
            Setup(),
            true
        )

        setupShutdownHook(httpServer)

        if (System.getenv("SHUTDOWN_TYPE") == "INPUT") {
            logger.info("Press any key to shutdown the server...")
            readLine()
            logger.info("Shutting down the server from input...")
            httpServer.shutdownNow()
        } else {
            logger.info("Server is running. Press Ctrl+C to shutdown.")
            Thread.currentThread().join()  // Espera indefinida
        }
    } catch (e: Exception) {
        logger.severe("Error starting server: ${e.message}")
    }
}

fun setupShutdownHook(httpServer: org.glassfish.grizzly.http.server.HttpServer) {
    Runtime.getRuntime().addShutdownHook(Thread {
        logger.info("Shutting down the server from shutdown hook...")
        httpServer.shutdownNow()
    })
}
