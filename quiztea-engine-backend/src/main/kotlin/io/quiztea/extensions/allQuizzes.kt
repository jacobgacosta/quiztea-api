package io.quiztea.extensions

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.koupper.container.app
import com.koupper.providers.aws.dynamo.DynamoClient

val allQuizzes: (Map<String, Any>) -> String = { params ->
    val page = params["page"]?.toString()?.toIntOrNull() ?: 0
    val size = 1

    val tableNameQuiz = "Quiztea_Quiz"
    val tableNamePoll = "Quiztea_Poll"
    val dynamoClient = app.getInstance(DynamoClient::class)

    val allItemsQuiz = dynamoClient.getAllItemsPaginated(tableNameQuiz)
    val allItemsPoll = dynamoClient.getAllItemsPaginated(tableNamePoll)

    val quizStart = page * size
    val pollStart = page * size

    val quizzesPage = if (quizStart < allItemsQuiz.size)
        allItemsQuiz.subList(quizStart, minOf(quizStart + size, allItemsQuiz.size))
    else emptyList()

    val pollsPage = if (pollStart < allItemsPoll.size)
        allItemsPoll.subList(pollStart, minOf(pollStart + size, allItemsPoll.size))
    else emptyList()

    val combined = (quizzesPage + pollsPage).sortedByDescending { it["createdAt"] as String }

    val totalElements = allItemsQuiz.size + allItemsPoll.size
    val totalPages = maxOf(
        (allItemsQuiz.size + size - 1) / size,
        (allItemsPoll.size + size - 1) / size
    )

    val pageResult = mapOf(
        "quiz" to quizzesPage,
        "poll" to pollsPage,
        "pageInfo" to Page(
            content = combined,
            pageNumber = page,
            pageSize = size * 2,
            totalElements = totalElements,
            totalPages = totalPages,
            hasNext = page + 1 < totalPages,
            hasPrevious = page > 0
        )
    )

    val mapper = jacksonObjectMapper()
    mapper.writeValueAsString(pageResult)
}