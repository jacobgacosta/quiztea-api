import com.koupper.octopus.process.ScriptProcessor

val setup: (ScriptProcessor) -> ScriptProcessor = { processor ->
    processor.register(
        "koupper-doc-web-functions",
        mutableMapOf(
            "server.port" to "8080",
            "server.rootUrl" to "api/v1"
        ),
        "LOCAL_AWS_DEPLOYMENT",
        "1.0.0",
        "dev.koupper",
        mapOf(
            "getGreeting" to "hello-world.kts",
        )
    ).run()

    processor
}