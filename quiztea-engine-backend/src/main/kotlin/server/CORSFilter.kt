package server

import jakarta.ws.rs.container.ContainerRequestContext
import jakarta.ws.rs.container.ContainerResponseContext
import jakarta.ws.rs.container.ContainerResponseFilter
import jakarta.ws.rs.ext.Provider

@Provider
class CORSFilter : ContainerResponseFilter {
    override fun filter(requestContext: ContainerRequestContext, responseContext: ContainerResponseContext) {
        val headers = responseContext.headers
        headers.add("Access-Control-Allow-Origin", "*") // Permite todos los orígenes; ajusta si es necesario
        headers.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
        headers.add("Access-Control-Allow-Headers", "Origin, Content-Type, Accept, Authorization")
        headers.add("Access-Control-Allow-Credentials", "true") // Opcional, si necesitas enviar cookies
    }
}