import com.koupper.container.app
import com.koupper.providers.files.JsonFileHandler
import com.koupper.providers.files.JsonFileHandlerImpl
import com.koupper.providers.files.toType
import com.koupper.providers.files.JsonParseException

val transformJson: () -> Unit = {
    /*
    val jsonFileHandler = app.createInstanceOf(JsonFileHandler::class) as JsonFileHandlerImpl<Map<String, String>>

    val jsonContent = jsonFileHandler.readFrom("C:\\Users\\dosek\\develop\\quiztea-engine\\src\\main\\resources\\poll.json")

    println(jsonContent.getText())
     */

    val executorSentence = "val result: Any = executor.call(functionName, mapOf("

    print(executorSentence.replace("Any", "ConchaTuMadre"))
}