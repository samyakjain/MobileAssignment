package com.samyak.githubsdk.interfaces

import com.samyak.githubsdk.models.GithubRepoModel

interface GithubSdkListener {
    fun onSearchedGithubRepositoryFetchSuccess(githubRepoModels: List<GithubRepoModel>)
    fun onSearchedGithubRepositoryFetchFailed()
}