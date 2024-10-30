import com.koupper.container.app
import com.koupper.providers.aws.dynamo.DynamoClient
import com.koupper.providers.files.*

val insertPollVotes: (Map<String, Any>) -> String = { params ->
    val txtFileHandler = app.createInstanceOf(TextFileHandler::class)

    val jsonObject: String =
        txtFileHandler.read("C:\\Users\\dosek\\develop\\quiztea-engine\\src\\main\\resources\\poll-votes.json")

    val dynamoClient = app.createInstanceOf(DynamoClient::class)

    val tableName = "Quiztea_Poll_Votes"

    try {
        dynamoClient.insertItem(tableName, jsonObject)

        println("Item inserted.")
    } catch (e: Exception) {
        println("Error inserting item: ${e.message}")
    }

    "jsonObject"
}
