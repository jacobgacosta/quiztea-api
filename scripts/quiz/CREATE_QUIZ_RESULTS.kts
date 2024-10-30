import com.koupper.container.interfaces.Container
import com.koupper.providers.aws.dynamo.DynamoClient

val createQuizResultsTable: (Container) -> Int = let@{ container ->
    val dynamoClient = container.createInstanceOf(DynamoClient::class)
    val quizResultsTable = "Quiztea_Quiz_Results"

    if (dynamoClient.doesTableExist(quizResultsTable)) {
        println("The table '$quizResultsTable' already exists.")
        return@let 200
    }

    val keySchema = listOf(
        Pair("quizId", "HASH"),
        Pair("userId", "RANGE")
    )

    val attributeDefinitions = listOf(
        Pair("quizId", "S"),
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
