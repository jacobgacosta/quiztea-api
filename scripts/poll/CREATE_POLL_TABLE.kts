import com.koupper.container.app
import com.koupper.providers.aws.dynamo.DynamoClient
import com.koupper.octopus.annotations.Export

@Export
val createPollTable: () -> Int = let@{
    val dynamoClient = app.getInstance(DynamoClient::class)

    val tableName = "Quiztea_Poll"

    if (dynamoClient.doesTableExist(tableName)) {
        println("The table '$tableName' already exists.")
        return@let 200
    }

    val keySchema = listOf(
        Pair("pollId", "HASH"),
        Pair("createdBy", "RANGE")
    )

    val attributeDefinitions = listOf(
        Pair("pollId", "S"),
        Pair("createdBy", "S")
    )

    val gsis = listOf(
        mapOf(
            "IndexName" to "CreatedByIndex",
            "KeySchema" to listOf(
                mapOf("AttributeName" to "createdBy", "KeyType" to "HASH")
            ),
            "Projection" to mapOf("ProjectionType" to "ALL"),
            "ProvisionedThroughput" to mapOf(
                "ReadCapacityUnits" to 5,
                "WriteCapacityUnits" to 5
            )
        ),
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
        )
    )

    try {
        dynamoClient.createTable(
            tableName = tableName,
            keySchema = keySchema,
            attributeDefinitions = attributeDefinitions,
            globalSecondaryIndexes = gsis
        )
        println("$tableName successfully created with GSIs.")
    } catch (e: Exception) {
        println("Error creating table: ${e.message}")
        return@let 500
    }

    200
}
