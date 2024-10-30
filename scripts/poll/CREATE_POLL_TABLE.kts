import com.koupper.container.interfaces.Container
import com.koupper.providers.aws.dynamo.DynamoClient

val createPollTable: (Container) -> Int = let@{ container ->
    val dynamoClient = container.createInstanceOf(DynamoClient::class)

    val tableName = "Quiztea_Poll"

    if (dynamoClient.doesTableExist(tableName)) {
        println("The table '$tableName' already exists.")
        return@let 200
    }

    val keySchema = listOf(
        Pair("id", "HASH"),
        Pair("createdBy", "RANGE")
    )

    val attributeDefinitions = listOf(
        Pair("id", "S"),
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
                mapOf("AttributeName" to "id", "KeyType" to "HASH")
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
