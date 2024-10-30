import com.koupper.container.app
import com.koupper.providers.aws.dynamo.DynamoClient
import com.koupper.providers.files.JsonFileHandler

val getPoll: (Map<String, Any>) -> String = { params ->
    val tableName = "Quiztea_Poll"

    val pollId = params["pollId"]

    val dynamoClient = app.createInstanceOf(DynamoClient::class)

    if (pollId == null) {
        val allItems: List<Map<String, Any>> = dynamoClient.getAllItemsPaginated(tableName)

        if (allItems.isNotEmpty()) {
            val textJsonParser = app.createInstanceOf(JsonFileHandler::class)
            textJsonParser.mapToJsonString(allItems)
        } else {
            "Item not found in $tableName."
        }
    } else {
        val item = dynamoClient.getItems(
            tableName = tableName,
            partitionKeyName = "id",
            partitionKeyValue = pollId as String,
            gsiName = "PollIdIndex"
        )?.first()

        item?.let {
            val textJsonParser = app.createInstanceOf(JsonFileHandler::class)
            textJsonParser.mapToJsonString(it)
        } ?: "Item not found in $tableName."
    }
}
