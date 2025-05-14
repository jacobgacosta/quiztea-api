package io.quiztea.http.handlers

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import io.quiztea.extensions.poll.getPollVotes
import io.quiztea.http.executor

class RequestHandlerGetPollVotes : RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    override fun handleRequest(input: APIGatewayProxyRequestEvent?, context: Context?): APIGatewayProxyResponseEvent {
        return try {
            val pollId = input?.pathParameters?.get("id") ?: createErrorResponse(404, "Votes were not found.")

            val result: String = executor.call(getPollVotes, mapOf("pollVotesId" to pollId))

            APIGatewayProxyResponseEvent().apply {
                statusCode = 200
                headers = input?.headers?.toMap() ?: emptyMap()
                body = result
            }
        } catch (e: IllegalArgumentException) {
            createErrorResponse(400, e.message)
        } catch (e: Exception) {
            createErrorResponse(500, "Internal server error: ${e.message}")
        }
    }

    private fun createErrorResponse(statusCode: Int, message: String?): APIGatewayProxyResponseEvent {
        return APIGatewayProxyResponseEvent().apply {
            this.statusCode = statusCode
            this.body = """{"error": "$message"}"""
            this.headers = mapOf("Content-Type" to "application/json")
        }
    }
}
