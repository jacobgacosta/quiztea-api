package io.quiztea.extensions.user

import com.koupper.container.app
import com.koupper.providers.aws.dynamo.DynamoClient
import com.koupper.providers.files.JSONFileHandler

val getUser: (Map<String, Any>) -> String = { params ->
    val tableName = "Users"
    val dynamoClient = app.createInstanceOf(DynamoClient::class)

    if (params["userId"] == null) {
        val allItems: List<Map<String, Any>> = dynamoClient.getAllItemsPaginated(tableName)
        if (allItems.isNotEmpty()) {
            val textJsonParser = app.createInstanceOf(JSONFileHandler::class)
            textJsonParser.mapToJsonString(allItems)
        } else {
            ""
        }
    } else {
        println("QuizIdIndex")
        val item = dynamoClient.getItems(
            tableName = tableName,
            partitionKeyName = "userId",
            partitionKeyValue = params["userId"] as String,
            gsiName = "UserIdIndex"
        )?.first()

        item?.let {
            val textJsonParser = app.createInstanceOf(JSONFileHandler::class)
            textJsonParser.mapToJsonString(it)
        } ?: "Item not found in $tableName."
    }
}

