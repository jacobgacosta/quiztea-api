package server.handlers

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import com.koupper.octopus.createDefaultConfiguration
import dev.koupper.extensions.helloWorld

val defaultConfig = createDefaultConfiguration()

class RequestHandlerGetGreeting : RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    override fun handleRequest(input: APIGatewayProxyRequestEvent?, context: Context?): APIGatewayProxyResponseEvent {
        exports.handler = async (event) => {
            const allowedDomain = "https://koupper.com";
            const origin = event.headers?.origin || event.headers?.referer || "";

            if (!origin.startsWith(allowedDomain)) {
                return {
                    statusCode: 403,
                    body: JSON.stringify({ message: "Access Denied" }),
                };
            }

            return {
                statusCode: 200,
                body: JSON.stringify({ message: "Success" }),
                    };
        };

        val result: String = defaultConfig.call(
            helloWorld, mapOf(
            "body" to (input?.body ?: ""),
            "queryParams" to  (input?.queryStringParameters?.entries?.joinToString("\n") { "${it.key}: ${it.value}" } ?: ""),
            "userAgent" to (input?.headers?.get("User-Agent") ?: ""),
            "headers" to (input?.headers?.entries?.joinToString("\n") { "${it.key}: ${it.value}" } ?: ""),
            "path" to (input?.path ?: "")
        ))

        return APIGatewayProxyResponseEvent().apply {
            statusCode = 200
            body = result
            headers = mapOf(
                "Access-Control-Allow-Origin" to "*",
                "Access-Control-Allow-Methods" to "POST, OPTIONS",
                "Access-Control-Allow-Headers" to "Content-Type, X-Amz-Date, Authorization, X-Api-Key, X-Amz-Security-Token"
            )
        }
    }
}
