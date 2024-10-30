import com.koupper.container.app
import com.koupper.providers.aws.dynamo.DynamoClient
import com.koupper.providers.files.TextFileHandler

val insertPoll: (Map<String, Any>) -> String = { params ->
    val dynamoClient = app.createInstanceOf(DynamoClient::class)

    val txtFileHandler = app.createInstanceOf(TextFileHandler::class)

    val jsonObject = txtFileHandler.read("C:\\Users\\dosek\\develop\\quiztea-engine\\src\\main\\resources\\poll.json")

    try {
        val tableName = "Quiztea_Poll"

        dynamoClient.insertItem(tableName, jsonObject)

        println("Item inserted.")
    } catch (e: Exception) {
        println("Error inserting item: ${e.message}")
    }

    "jsonObject"
}
