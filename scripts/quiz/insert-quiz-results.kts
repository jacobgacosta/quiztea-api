import com.koupper.container.app
import com.koupper.container.interfaces.Container
import com.koupper.providers.aws.dynamo.DynamoClient
import com.koupper.providers.files.*
import java.util.UUID

val insertQuizResults: (Map<String, Any>) -> String = { params ->
    val txtFileHandler = app.createInstanceOf(TextFileHandler::class)

    val quizResult =
        txtFileHandler.read("C:\\Users\\dosek\\develop\\quiztea-engine\\src\\main\\resources\\quiz-results.json")

    data class QuizResult(
        val quizId: String,
        val userId: String,
        val startTime: String,
        val endTime: String,
        val score: Int,
        val numberOfQuestions: Int,
        val userAnswers: List<Map<String, String>>
    )

    val jsonFileHandler = app.createInstanceOf(JsonFileHandler::class) as JsonFileHandlerImpl<QuizResult>

    val qr = jsonFileHandler.read(quizResult).toType()

    val dynamoClient = app.createInstanceOf(DynamoClient::class)

    val quizTable = "Quiztea_Quiz"

    val quiz = dynamoClient.getItems(
        tableName = quizTable,
        partitionKeyName = "id",
        partitionKeyValue = qr.quizId,
        gsiName = "QuizIdIndex"
    )

    val registeredQuestions = quiz?.first()?.get("questions") as List<Map<String, String>>

    var correctAnswers = 0

    for (userAnswer in qr.userAnswers) {
        val selectedUserQuestionId = userAnswer["questionId"]

        val selectedUserAnswer = userAnswer["answer"]

        for (registeredQuestion in registeredQuestions) {
            if (selectedUserQuestionId == registeredQuestion["questionId"] && selectedUserAnswer == registeredQuestion["correctAnswer"]) {
                correctAnswers += 1
            }
        }
    }

    val quizResultsInfo = QuizResult(
        qr.quizId,
        qr.userId,
        qr.startTime,
        qr.endTime,
        correctAnswers,
        registeredQuestions.size,
        qr.userAnswers
    )

    val quizResultsInfoJson: String = jsonFileHandler.toJsonString(quizResultsInfo)

    try {
        val quizResultsTable = "Quiztea_Quiz_Results"

        dynamoClient.insertItem(quizResultsTable, quizResultsInfoJson)

        println("Item inserted.")
    } catch (e: Exception) {
        println("Error inserting item: ${e.message}")
    }

    "quizResultsInfoJson"
}
