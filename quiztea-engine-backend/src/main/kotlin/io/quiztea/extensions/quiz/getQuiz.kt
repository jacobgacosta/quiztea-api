package io.quiztea.extensions.quiz

import com.koupper.container.app
import com.koupper.providers.aws.dynamo.DynamoClient
import com.koupper.providers.files.JSONFileHandler

val getQuiz: (Map<String, Any>) -> String = { params ->
    val tableName = "Quiztea_Quiz"
    val dynamoClient = app.getInstance(DynamoClient::class)

    if (params["quizId"] == null) {
        val allItems: List<Map<String, Any>> = dynamoClient.getAllItemsPaginated(tableName)

        if (allItems.isNotEmpty()) {
            val textJsonParser = app.getInstance(JSONFileHandler::class)
            textJsonParser.mapToJsonString(allItems)
        } else {
            ""
        }
    } else {
        println("QuizIdIndex")
        val item = dynamoClient.getItems(
            tableName = tableName,
            partitionKeyName = "quizId",
            partitionKeyValue = params["quizId"] as String,
            gsiName = "QuizIdIndex"
        )?.first()

        item?.let {
            val textJsonParser = app.getInstance(JSONFileHandler::class)
            textJsonParser.mapToJsonString(it)
        } ?: "Item not found in $tableName."
    }
}

