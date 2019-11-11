package com.samyak.githubsdk.models

data class GithubRepositorySchema(
    var name: String,
    var isPrivate: Boolean?,
    var description: String,
    var language: String,
    var forks: Int?,
    var openIssues: Int?,
    var watchers: Int?
) {
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val githubRepositorySchema = o as GithubRepositorySchema?
        return name == githubRepositorySchema!!.name && language == githubRepositorySchema.language
    }

    override fun toString(): String {
        return "GithubRepositorySchema{" +
                "mName='" + name + '\''.toString() +
                ", mIsPrivate=" + isPrivate +
                ", mDescription='" + description + '\''.toString() +
                ", mLanguage='" + language + '\''.toString() +
                ", mForks=" + forks +
                ", mOpenIssues=" + openIssues +
                ", mWatchers=" + watchers +
                '}'.toString()
    }
}