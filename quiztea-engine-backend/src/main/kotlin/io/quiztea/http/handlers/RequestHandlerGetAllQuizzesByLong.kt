package io.quiztea.http.handlers

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import io.quiztea.extensions.allQuizzes
import io.quiztea.http.executor

class RequestHandlerGetAllQuizzesByLong : RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    override fun handleRequest(input: APIGatewayProxyRequestEvent?, context: Context?): APIGatewayProxyResponseEvent {
        val page = input?.pathParameters?.get("page") ?: throw IllegalArgumentException("Page is missing")
        val result: String = executor.call(allQuizzes, mapOf("page" to page))
        return APIGatewayProxyResponseEvent().apply {
            statusCode = 200
            headers = input?.headers?.toMap() ?: emptyMap()
            body = result
        }
    }
}
