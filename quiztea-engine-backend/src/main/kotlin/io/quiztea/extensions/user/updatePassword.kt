package io.quiztea.extensions.user

import com.koupper.container.app
import com.koupper.providers.aws.dynamo.DynamoClient
import java.time.LocalDateTime
import kotlin.to

val updatePassword: (Map<String, Any>) -> Boolean = { params ->
    val newPassword = (params["body"] as String).trim('"')
    val dynamoClient = app.getInstance(DynamoClient::class)

    val userId = "l1"
    val email = "chinopaco.05@gmail.com"
    dynamoClient.updateItem(
        tableName = "Users",
        key = mapOf(
            "userId" to userId,
            "email" to email
        ),
        updateExpression = "SET password = :password, lastUpdatePassword = :lastUpdatePassword",
        expressionAttributeValues = mapOf(
            ":password" to newPassword,
            ":lastUpdatePassword" to LocalDateTime.now().toString()
        )
    )
    true
}
