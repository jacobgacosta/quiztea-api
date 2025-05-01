package io.quiztea.extensions.quiz

import com.koupper.container.app
import com.koupper.providers.aws.dynamo.DynamoClient
import com.koupper.providers.files.JSONFileHandler

val getQuiz: (Map<String, Any>) -> String = { params ->
    val tableName = "Quiztea_Quiz"

    val quizId = params["quizId"] as? String ?: throw IllegalArgumentException("quizId cannot be null")

    val dynamoClient = app.createInstanceOf(DynamoClient::class)

    val item = dynamoClient.getItems(
        tableName = tableName,
        partitionKeyName = "id",
        partitionKeyValue = quizId,
        gsiName = "QuizIdIndex"
    )?.first()

    item?.let {
        val textJsonParser = app.createInstanceOf(JSONFileHandler::class)
        textJsonParser.mapToJsonString(it)
    } ?: "Item not found in $tableName."
}

