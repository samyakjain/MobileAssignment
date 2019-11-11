package com.samyak.githubsdk.models

data class GithubRepoModel(
    var name: String,
    var isPrivate: Boolean?,
    var description: String,
    var language: String
) {

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val githubRepoModel = o as GithubRepoModel?
        return name == githubRepoModel!!.name && language == githubRepoModel.language
    }


}