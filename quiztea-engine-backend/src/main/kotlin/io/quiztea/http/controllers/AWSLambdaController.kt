package io.quiztea.http.controllers

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import jakarta.ws.rs.*
import jakarta.ws.rs.core.Context
import jakarta.ws.rs.core.HttpHeaders
import jakarta.ws.rs.core.UriInfo
import io.quiztea.http.handlers.*

@Path("api/v1")
class AWSLambdaController {
    @Context
    private lateinit var uriInfo: UriInfo
    @Context
    private lateinit var inputHeaders: HttpHeaders

    @POST
    @Path("/poll")
    @Consumes("application/json")
    @Produces("application/json")
    fun insertPoll(
        bodyJson: String
    ): APIGatewayProxyResponseEvent {
        val apiGatewayProxyRequestEvent = APIGatewayProxyRequestEvent().apply {
            path = uriInfo.path
            httpMethod = "POST"
            headers = inputHeaders.requestHeaders.mapValues { it.value.joinToString(",") }
            queryStringParameters = uriInfo.queryParameters.mapValues { it.value.joinToString(",") }
            body = bodyJson
        }

        val requestHandlerInsertPoll = RequestHandlerInsertPoll()

        return requestHandlerInsertPoll.handleRequest(apiGatewayProxyRequestEvent, null)
    }

    @POST
    @Path("/poll/{id}/votes")
    @Consumes("application/json")
    @Produces("application/json")
    fun insertPollVotes(
        bodyJson: String
    ): APIGatewayProxyResponseEvent {
        val apiGatewayProxyRequestEvent = APIGatewayProxyRequestEvent().apply {
            path = uriInfo.path
            httpMethod = "POST"
            headers = inputHeaders.requestHeaders.mapValues { it.value.joinToString(",") }
            queryStringParameters = uriInfo.queryParameters.mapValues { it.value.joinToString(",") }
            body = bodyJson
        }

        val requestHandlerInsertPollVotes = RequestHandlerInsertPollVotes()

        return requestHandlerInsertPollVotes.handleRequest(apiGatewayProxyRequestEvent, null)
    }

    @POST
    @Path("/quiz")
    @Consumes("application/json")
    @Produces("application/json")
    fun insertQuiz(
        bodyJson: String
    ): APIGatewayProxyResponseEvent {
        val apiGatewayProxyRequestEvent = APIGatewayProxyRequestEvent().apply {
            path = uriInfo.path
            httpMethod = "POST"
            headers = inputHeaders.requestHeaders.mapValues { it.value.joinToString(",") }
            queryStringParameters = uriInfo.queryParameters.mapValues { it.value.joinToString(",") }
            body = bodyJson
        }

        val requestHandlerInsertQuiz = RequestHandlerInsertQuiz()

        return requestHandlerInsertQuiz.handleRequest(apiGatewayProxyRequestEvent, null)
    }

    @POST
    @Path("/quiz/{id}/results")
    @Consumes("application/json")
    @Produces("application/json")
    fun insertQuizResults(
        bodyJson: String
    ): APIGatewayProxyResponseEvent {
        val apiGatewayProxyRequestEvent = APIGatewayProxyRequestEvent().apply {
            path = uriInfo.path
            httpMethod = "POST"
            headers = inputHeaders.requestHeaders.mapValues { it.value.joinToString(",") }
            queryStringParameters = uriInfo.queryParameters.mapValues { it.value.joinToString(",") }
            body = bodyJson
        }

        val requestHandlerInsertQuizResults = RequestHandlerInsertQuizResults()

        return requestHandlerInsertQuizResults.handleRequest(apiGatewayProxyRequestEvent, null)
    }

    @GET
    @Path("/poll")
    @Consumes("application/json")
    @Produces("application/json")
    fun getPolls(

    ): APIGatewayProxyResponseEvent {
        val apiGatewayProxyRequestEvent = APIGatewayProxyRequestEvent().apply {
            path = uriInfo.path
            httpMethod = "GET"
            headers = inputHeaders.requestHeaders.mapValues { it.value.joinToString(",") }
            queryStringParameters = uriInfo.queryParameters.mapValues { it.value.joinToString(",") }
            body = null
        }

        val requestHandlerGetPolls = RequestHandlerGetPolls()

        return requestHandlerGetPolls.handleRequest(apiGatewayProxyRequestEvent, null)
    }

    @GET
    @Path("/poll/{id}")
    @Consumes("application/json")
    @Produces("application/json")
    fun getPollById(

    ): APIGatewayProxyResponseEvent {
        val apiGatewayProxyRequestEvent = APIGatewayProxyRequestEvent().apply {
            pathParameters = uriInfo.pathParameters.mapValues { it.value.firstOrNull() ?: "" }
            path = uriInfo.path
            httpMethod = "GET"
            headers = inputHeaders.requestHeaders.mapValues { it.value.joinToString(",") }
            queryStringParameters = uriInfo.queryParameters.mapValues { it.value.joinToString(",") }
            body = null
        }

        val requestHandlerGetPollById = RequestHandlerGetPollById()

        return requestHandlerGetPollById.handleRequest(apiGatewayProxyRequestEvent, null)
    }

    @GET
    @Path("/poll/{id}/votes")
    @Consumes("application/json")
    @Produces("application/json")
    fun getPollVotes(

    ): APIGatewayProxyResponseEvent {
        val apiGatewayProxyRequestEvent = APIGatewayProxyRequestEvent().apply {
            pathParameters = uriInfo.pathParameters.mapValues { it.value.firstOrNull() ?: "" }
            path = uriInfo.path
            httpMethod = "GET"
            headers = inputHeaders.requestHeaders.mapValues { it.value.joinToString(",") }
            queryStringParameters = uriInfo.queryParameters.mapValues { it.value.joinToString(",") }
            body = null
        }

        val requestHandlerGetPollVotes = RequestHandlerGetPollVotes()

        return requestHandlerGetPollVotes.handleRequest(apiGatewayProxyRequestEvent, null)
    }

    @GET
    @Path("/poll/{pollId}/votes/{voteId}")
    @Consumes("application/json")
    @Produces("application/json")
    fun getPollVoteById(

    ): APIGatewayProxyResponseEvent {
        val apiGatewayProxyRequestEvent = APIGatewayProxyRequestEvent().apply {
            path = uriInfo.path
            httpMethod = "GET"
            headers = inputHeaders.requestHeaders.mapValues { it.value.joinToString(",") }
            queryStringParameters = uriInfo.queryParameters.mapValues { it.value.joinToString(",") }
            body = null
        }

        val requestHandlerGetPollVoteById = RequestHandlerGetPollVoteById()

        return requestHandlerGetPollVoteById.handleRequest(apiGatewayProxyRequestEvent, null)
    }

    @GET
    @Path("/quiz")
    @Consumes("application/json")
    @Produces("application/json")
    fun getQuiz(

    ): APIGatewayProxyResponseEvent {
        val apiGatewayProxyRequestEvent = APIGatewayProxyRequestEvent().apply {
            path = uriInfo.path
            httpMethod = "GET"
            headers = inputHeaders.requestHeaders.mapValues { it.value.joinToString(",") }
            queryStringParameters = uriInfo.queryParameters.mapValues { it.value.joinToString(",") }
            body = null
        }

        val requestHandlerGetQuiz = RequestHandlerGetQuiz()

        return requestHandlerGetQuiz.handleRequest(apiGatewayProxyRequestEvent, null)
    }

    @GET
    @Path("/quiz/{id}")
    @Consumes("application/json")
    @Produces("application/json")
    fun getQuizById(

    ): APIGatewayProxyResponseEvent {
        val apiGatewayProxyRequestEvent = APIGatewayProxyRequestEvent().apply {
            pathParameters = uriInfo.pathParameters.mapValues { it.value.firstOrNull() ?: "" }
            path = uriInfo.path
            httpMethod = "GET"
            headers = inputHeaders.requestHeaders.mapValues { it.value.joinToString(",") }
            queryStringParameters = uriInfo.queryParameters.mapValues { it.value.joinToString(",") }
            body = null
        }

        val requestHandlerGetQuizById = RequestHandlerGetQuizById()

        return requestHandlerGetQuizById.handleRequest(apiGatewayProxyRequestEvent, null)
    }

    @GET
    @Path("/quiz/{quizId}/results")
    @Consumes("application/json")
    @Produces("application/json")
    fun getQuizResults(

    ): APIGatewayProxyResponseEvent {
        val apiGatewayProxyRequestEvent = APIGatewayProxyRequestEvent().apply {
            path = uriInfo.path
            httpMethod = "GET"
            headers = inputHeaders.requestHeaders.mapValues { it.value.joinToString(",") }
            queryStringParameters = uriInfo.queryParameters.mapValues { it.value.joinToString(",") }
            body = null
        }

        val requestHandlerGetQuizResults = RequestHandlerGetQuizResults()

        return requestHandlerGetQuizResults.handleRequest(apiGatewayProxyRequestEvent, null)
    }

    @GET
    @Path("/quiz/{quizId}/results/{resultId}")
    @Consumes("application/json")
    @Produces("application/json")
    fun getQuizResultsById(

    ): APIGatewayProxyResponseEvent {
        val apiGatewayProxyRequestEvent = APIGatewayProxyRequestEvent().apply {
            path = uriInfo.path
            httpMethod = "GET"
            headers = inputHeaders.requestHeaders.mapValues { it.value.joinToString(",") }
            queryStringParameters = uriInfo.queryParameters.mapValues { it.value.joinToString(",") }
            body = null
        }

        val requestHandlerGetQuizResultsById = RequestHandlerGetQuizResultsById()

        return requestHandlerGetQuizResultsById.handleRequest(apiGatewayProxyRequestEvent, null)
    }

}