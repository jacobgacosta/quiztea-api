package io.quiztea.extensions.quiz

import com.koupper.container.app
import com.koupper.providers.aws.dynamo.DynamoClient
import com.koupper.providers.files.JSONFileHandler

val getQuizResults: (Map<String, Any>) -> String = { params ->
    val tableName = "Quiztea_Quiz_Results"

    val userId = params["userId"] as? String ?: throw IllegalArgumentException("userId cannot be null")

    val quizId = params["quizId"] as? String ?: throw IllegalArgumentException("quizId cannot be null")

    val dynamoClient = app.getInstance(DynamoClient::class)

    val item = dynamoClient.getItem(
        tableName = tableName,
        partitionKeyName = "quizId",
        partitionKeyValue = quizId,
        sortKeyName = "userId",
        sortKeyValue = userId
    )

    item?.let {
        val textJsonParser = app.getInstance(JSONFileHandler::class)
        textJsonParser.mapToJsonString(it)
    } ?: "Item not found in $tableName."
}

