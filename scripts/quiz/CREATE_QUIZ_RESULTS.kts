import com.koupper.container.app
import com.koupper.providers.aws.dynamo.DynamoClient
import com.koupper.octopus.annotations.Export

@Export
val createQuizResultsTable: () -> Int = let@{
    val dynamoClient = app.getInstance(DynamoClient::class)
    val quizResultsTable = "Quiztea_Quiz_Results"

    if (dynamoClient.doesTableExist(quizResultsTable)) {
        println("The table '$quizResultsTable' already exists.")
        return@let 200
    }

    val keySchema = listOf(
        Pair("quizResultsId", "HASH"),
        Pair("userId", "RANGE")
    )

    val attributeDefinitions = listOf(
        Pair("quizResultsId", "S"),
        Pair("userId", "S")
    )

    try {
        dynamoClient.createTable(
            tableName = quizResultsTable,
            keySchema = keySchema,
            attributeDefinitions = attributeDefinitions
        )
        println("$quizResultsTable successfully created.")
    } catch (e: Exception) {
        println("Error creating table: ${e.message}")
        return@let 500
    }

    200
}
