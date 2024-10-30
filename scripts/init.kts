import com.koupper.octopus.process.ScriptProcessor

val setup: (ScriptProcessor) -> Unit = { processor ->
    processor.register(
        "quiztea-engine",
        mutableMapOf(
            "server.port" to "8080",
            "server.rootUrl" to "api/v1"
        ),
        "LOCAL_AWS_DEPLOYMENT",
        "1.0.0",
        "io.quiztea",
        mapOf(
            "getPolls" to "poll/get-poll.kts",
            "getPollById" to "poll/get-poll.kts",
            "getPollVotes" to "poll/get-poll-votes.kts",
            "getPollVoteById" to "poll/get-poll-votes.kts",
            "insertPoll" to "poll/insert-poll.kts",
            "insertPollVotes" to "poll/insert-poll-votes.kts",
            "getQuiz" to "quiz/get-quiz.kts",
            "getQuizById" to "quiz/get-quiz.kts",
            "getQuizResults" to "quiz/get-quiz-results.kts",
            "getQuizResultsById" to "quiz/get-quiz-results.kts",
            "insertQuiz" to "quiz/insert-quiz.kts",
            "insertQuizResults" to "quiz/insert-quiz-results.kts"
        )
    ).run()
}