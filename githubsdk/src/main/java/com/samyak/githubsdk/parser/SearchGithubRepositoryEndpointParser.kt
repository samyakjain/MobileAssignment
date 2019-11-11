package com.samyak.githubsdk.parser

import com.samyak.githubsdk.models.GithubRepositorySchema
import org.json.JSONObject
import java.util.*

class SearchGithubRepositoryEndpointParser {

    fun parseAndReturnGithubRepositorySearchResponse(body: String?): List<GithubRepositorySchema> {
        val githubRepositorySchemas = ArrayList<GithubRepositorySchema>()
        if (body != null) {
            val response = JSONObject(body)
            if (response.has("items")) {
                val items = response.getJSONArray("items")
                for (i in 0 until items.length()) {
                    val repoObj = items.getJSONObject(i)
                    if (!repoObj.has("name") || !repoObj.has("private") || !repoObj.has("description") || !repoObj.has("language") || !repoObj.has("forks_count") || !repoObj.has("open_issues") || !repoObj.has("watchers")) {
                        throw ParsingException("Incorrect Json - keys are missing in objects")
                    }
                    githubRepositorySchemas.add(
                        GithubRepositorySchema(
                            repoObj.getString("name"),
                            repoObj.getBoolean("private"),
                            repoObj.getString("description"),
                            repoObj.getString("language"),
                            repoObj.getInt("forks_count"),
                            repoObj.getInt("open_issues"),
                            repoObj.getInt("watchers")
                        )
                    )
                }
            } else {
                throw ParsingException("Incorrect Json - items key is missing")
            }
        } else {
            throw NullPointerException()
        }

        return githubRepositorySchemas
    }
}

