import com.koupper.container.app
import com.koupper.container.interfaces.Container
import com.koupper.providers.aws.dynamo.DynamoClient
import com.koupper.providers.files.TextFileHandler

val insertQuiz: (Map<String, Any>) -> String = { params ->
    val dynamoClient = app.createInstanceOf(DynamoClient::class)

    val txtFileHandler = app.createInstanceOf(TextFileHandler::class)

    val jsonObject: String =
        txtFileHandler.read("C:\\Users\\dosek\\develop\\quiztea-engine\\src\\main\\resources\\quiz.json") as String

    try {
        val tableName = "Quiztea_Quiz"

        dynamoClient.insertItem(tableName, jsonObject)
        println("Item inserted.")
    } catch (e: Exception) {
        println("Error inserting item: ${e.message}")
    }

    "jsonObject"
}
