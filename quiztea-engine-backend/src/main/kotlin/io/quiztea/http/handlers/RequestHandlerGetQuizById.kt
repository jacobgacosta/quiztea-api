package io.quiztea.http.handlers

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import io.quiztea.extensions.poll.getPoll
import io.quiztea.extensions.quiz.getQuiz
import io.quiztea.http.executor

class RequestHandlerGetQuizById : RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    override fun handleRequest(input: APIGatewayProxyRequestEvent?, context: Context?): APIGatewayProxyResponseEvent {
        val quizId = input?.pathParameters?.get("id") ?: throw IllegalArgumentException("Quiz ID is missing")
        val result: String = executor.call(getQuiz, mapOf("quizId" to quizId))

        return APIGatewayProxyResponseEvent().apply {
            statusCode = 200
            headers = input?.headers?.toMap() ?: emptyMap()
            body = result
        }
    }
}
