package io.quiztea.extensions.user

import com.koupper.container.app
import com.koupper.providers.aws.dynamo.DynamoClient
import com.koupper.providers.files.JSONFileHandler

val getUser: (Map<String, Any>) -> String = { params ->
    val tableName = "Users"
    val dynamoClient = app.getInstance(DynamoClient::class)
    val userId = "1";
    val item = dynamoClient.getItems(
        tableName = tableName,
        partitionKeyName = "userId",
        partitionKeyValue = userId,
        gsiName = "UserIdIndex"
    )?.first()

    item?.let {
        val textJsonParser = app.getInstance(JSONFileHandler::class)
        textJsonParser.mapToJsonString(it)
    } ?: "Item not found in $tableName."
}

