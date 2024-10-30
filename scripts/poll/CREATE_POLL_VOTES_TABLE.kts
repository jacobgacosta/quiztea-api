import com.koupper.container.interfaces.Container
import com.koupper.providers.aws.dynamo.DynamoClient

val createPollResultsTable: (Container) -> Int = let@{ container ->
    val dynamoClient = container.createInstanceOf(DynamoClient::class)
    val tableName = "Quiztea_Poll_Votes"

    if (dynamoClient.doesTableExist(tableName)) {
        println("The table '$tableName' already exists.")
        return@let 200
    }

    val keySchema = listOf(
        Pair("pollId", "HASH"),
        Pair("userId", "RANGE")
    )

    val attributeDefinitions = listOf(
        Pair("pollId", "S"),
        Pair("userId", "S")
    )

    val gsis = listOf(
        mapOf(
            "IndexName" to "PollIdIndex",
            "KeySchema" to listOf(
                mapOf("AttributeName" to "pollId", "KeyType" to "HASH")
            ),
            "Projection" to mapOf("ProjectionType" to "ALL"),
            "ProvisionedThroughput" to mapOf(
                "ReadCapacityUnits" to 5,
                "WriteCapacityUnits" to 5
            )
        ),
        mapOf(
            "IndexName" to "UserIdIndex",
            "KeySchema" to listOf(
                mapOf("AttributeName" to "userId", "KeyType" to "HASH")
            ),
            "Projection" to mapOf("ProjectionType" to "ALL"),
            "ProvisionedThroughput" to mapOf(
                "ReadCapacityUnits" to 5,
                "WriteCapacityUnits" to 5
            )
        )
    )

    try {
        dynamoClient.createTable(
            tableName = tableName,
            keySchema = keySchema,
            attributeDefinitions = attributeDefinitions,
            globalSecondaryIndexes = gsis
        )
        println("$tableName successfully created.")
    } catch (e: Exception) {
        println("Error creating table: ${e.message}")
        return@let 500
    }

    200
}
