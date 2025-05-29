package io.quiztea.extensions.user

import com.koupper.container.app
import com.koupper.providers.aws.dynamo.DynamoClient
import com.koupper.providers.files.TextFileHandler

val insertUser: (Map<String, Any>) -> String = { params ->
    val dynamoClient = app.getInstance(DynamoClient::class)

    val txtFileHandler = app.getInstance(TextFileHandler::class)

    val jsonObject: String = params["body"] as String

    try {
        val tableName = "Users"

        dynamoClient.insertItem(tableName, jsonObject)
        println("Item inserted.")
    } catch (e: Exception) {
        println("Error inserting item: ${e.message}")
    }

    "jsonObject"
}
