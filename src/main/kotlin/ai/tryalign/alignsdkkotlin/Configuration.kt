package ai.tryalign.alignsdkkotlin

class Configuration (
    val projectId: String,
    val apiKey: String,
    val apiHost: String = Constants.DEFAULT_API_HOST,
) {
    fun isValid(): Boolean {
        return projectId.isNotEmpty() && apiKey.isNotEmpty() && apiHost.startsWith("https://")
    }
}