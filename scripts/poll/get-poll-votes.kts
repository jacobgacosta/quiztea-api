import com.koupper.container.app
import com.koupper.providers.aws.dynamo.DynamoClient
import com.koupper.providers.files.JsonFileHandler
import com.koupper.providers.files.JsonFileHandlerImpl

val getPollVotes: (Map<String, Any>) -> String = { params ->
    data class Option(val id: String, val description: String, val totalOfVotes: Int)

    data class VotesInfo(val pollId: String, val votes: List<Option>)

    val pollId = params["pollId"] as? String

    val dynamoClient = app.createInstanceOf(DynamoClient::class)

    val poll = dynamoClient.getItems("Quiztea_Poll", "id", pollId, "PollIdIndex")?.first()

    var votesInfo = "{}"

    if (poll != null) {
        val pollVotesTable = "Quiztea_Poll_Votes"

        val pollOptions = poll["options"] as List<Map<String, Any>>

        val options = mutableListOf<Option>()

        for (pullOption in pollOptions) {
            val optionId = pullOption["optionId"]

            val votes = dynamoClient.getItems(
                tableName = pollVotesTable,
                partitionKeyName = "pollId",
                partitionKeyValue = pollId,
                gsiName = "PollIdIndex",
                filterExpression = "contains(selectedOptionId, :filterValue)",
                comparisonValue = optionId as String
            )

            var votesCounter = 0

            if (!votes.isNullOrEmpty()) {
                votesCounter = votes.count()
            }

            options.add(Option(optionId, pullOption["text"] as String, votesCounter))
        }

        val jsonFileHandler = app.createInstanceOf(JsonFileHandler::class) as JsonFileHandlerImpl<VotesInfo>
        votesInfo = jsonFileHandler.toJsonString(VotesInfo(poll["id"] as String, options))
    }

    votesInfo
}

