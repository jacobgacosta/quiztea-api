import com.koupper.container.app
import com.koupper.providers.aws.dynamo.DynamoClient
import com.koupper.octopus.annotations.Export

@Export
val createQuizTable: () -> Int = let@{
    val dynamoClient = app.getInstance(DynamoClient::class)

    val quizTable = "Quiztea_Quiz"

    if (dynamoClient.doesTableExist(quizTable)) {
        println("The table '$quizTable' already exists.")
        return@let 200
    }

    val keySchema = listOf(
        Pair("quizId", "HASH"),
        Pair("createdBy", "RANGE")
    )

    val attributeDefinitions = listOf(
        Pair("quizId", "S"),
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
            "IndexName" to "QuizIdIndex",
            "KeySchema" to listOf(
                mapOf("AttributeName" to "quizId", "KeyType" to "HASH")
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
            tableName = quizTable,
            keySchema = keySchema,
            attributeDefinitions = attributeDefinitions,
            globalSecondaryIndexes = gsis
        )
        println("$quizTable successfully created.")
    } catch (e: Exception) {
        println("Error creating table: ${e.message}")
        return@let 500
    }

    200
}
