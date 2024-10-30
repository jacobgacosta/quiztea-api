import com.koupper.container.app
import com.koupper.container.interfaces.Container
import com.koupper.providers.aws.dynamo.DynamoClient
import com.koupper.providers.files.JsonFileHandler

val getQuiz: (Map<String, Any>) -> String = { params ->
    val tableName = "Quiztea_Quiz"

    val quizId = params["quizId"] as? String ?: throw IllegalArgumentException("quizId cannot be null")

    val dynamoClient = app.createInstanceOf(DynamoClient::class)

    val item = dynamoClient.getItems(
        tableName = tableName,
        partitionKeyName = "id",
        partitionKeyValue = quizId,
        gsiName = "QuizIdIndex"
    )?.first()

    item?.let {
        val textJsonParser = app.createInstanceOf(JsonFileHandler::class)
        textJsonParser.mapToJsonString(it)
    } ?: "Item not found in $tableName."
}

