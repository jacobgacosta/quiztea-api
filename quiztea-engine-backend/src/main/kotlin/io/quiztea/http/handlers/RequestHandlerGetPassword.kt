package io.quiztea.http.handlers

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import io.quiztea.extensions.user.getPassword
import io.quiztea.http.executor

class RequestHandlerGetPassword : RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    override fun handleRequest(input: APIGatewayProxyRequestEvent?, context: Context?): APIGatewayProxyResponseEvent {
        val result: Map<String, String?>? = executor.call(getPassword, mapOf(
            "body" to (input?.body ?: ""),
            "queryParams" to (input?.queryStringParameters?.entries
                ?.joinToString("\n") { "${it.key}: ${it.value}" } ?: ""),
            "userAgent" to (input?.headers?.get("User-Agent") ?: ""),
            "headers" to (input?.headers?.entries
                ?.joinToString("\n") { "${it.key}: ${it.value}" } ?: ""),
            "path" to (input?.path ?: "")
        ) as Map<String, Any>)

        return APIGatewayProxyResponseEvent().apply {
            statusCode = 200
            headers = input?.headers?.toMap() ?: emptyMap()
            body = result.toString()
        }
    }
}
