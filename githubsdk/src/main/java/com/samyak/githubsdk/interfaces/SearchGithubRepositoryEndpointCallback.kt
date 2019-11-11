package com.samyak.githubsdk.interfaces

import com.samyak.githubsdk.models.GithubRepositorySchema

interface SearchGithubRepositoryEndpointCallback {
    fun onGithubRepositoryFetchSucceeded(repositorySchemaList: List<GithubRepositorySchema>)
    fun onGithubRepositoryFetchFailed()
}