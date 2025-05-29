package io.quiztea.extensions.user

import com.koupper.container.app
import com.koupper.providers.aws.dynamo.DynamoClient
import com.koupper.providers.files.JSONFileHandler

val getPassword: (Map<String, Any>) -> Map<String, String?>? = { params ->
    val tableName = "Users"
    val dynamoClient = app.getInstance(DynamoClient::class)
    val userId = "l1"
    val item = dynamoClient.getItems(
        tableName = tableName,
        partitionKeyName = "userId",
        partitionKeyValue = userId,
        gsiName = "UserIdIndex"
    )?.first()

    item?.let {
        mapOf(
            "password" to it["password"] as? String,
            "lastUpdatePassword" to it["lastUpdatePassword"] as? String
        )
    }
}

