package io.quiztea.extensions.poll

import com.koupper.container.app
import com.koupper.providers.aws.dynamo.DynamoClient
import com.koupper.providers.files.JSONFileHandler

val getPoll: (Map<String, Any>) -> String = { params ->
    val tableName = "Quiztea_Poll"

    val dynamoClient = app.getInstance(DynamoClient::class)

    if (params["pollId"] == null) {
        val allItems: List<Map<String, Any>> = dynamoClient.getAllItemsPaginated(tableName)

        if (allItems.isNotEmpty()) {
            val textJsonParser = app.getInstance(JSONFileHandler::class)
            textJsonParser.mapToJsonString(allItems)
        } else {
            ""
        }
    } else {
        val item = dynamoClient.getItems(
            tableName = tableName,
            partitionKeyName = "pollId",
            partitionKeyValue = params["pollId"] as String,
            gsiName = "PollIdIndex"
        )?.first()

        item?.let {
            val textJsonParser = app.getInstance(JSONFileHandler::class)
            textJsonParser.mapToJsonString(it)
        } ?: "Item not found in $tableName."
    }
}
