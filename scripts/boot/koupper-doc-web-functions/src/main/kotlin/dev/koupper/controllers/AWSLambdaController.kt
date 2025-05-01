package dev.koupper.controllers

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import jakarta.ws.rs.*
import jakarta.ws.rs.core.Context
import jakarta.ws.rs.core.HttpHeaders
import jakarta.ws.rs.core.UriInfo
import server.handlers.*

@Path("api/v1")
class AWSLambdaController {
    @Context
    private lateinit var uriInfo: UriInfo
    @Context
    private lateinit var inputHeaders: HttpHeaders

    @POST
    @Path("/greetings")
    @Consumes("application/json")
    @Produces("application/json")
    fun getGreeting(
        bodyJson: String
    ): APIGatewayProxyResponseEvent {
        val apiGatewayProxyRequestEvent = APIGatewayProxyRequestEvent().apply {
            path = uriInfo.path
            httpMethod = "POST"
            headers = inputHeaders.requestHeaders.mapValues { it.value.joinToString(",") }
            queryStringParameters = uriInfo.queryParameters.mapValues { it.value.joinToString(",") }
            body = bodyJson
        }

        val requestHandlerGetGreeting = RequestHandlerGetGreeting()

        return requestHandlerGetGreeting.handleRequest(apiGatewayProxyRequestEvent, null)
    }

}
