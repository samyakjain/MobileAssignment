package com.samyak.githubsdk

import com.samyak.githubsdk.interfaces.GithubSdkListener
import com.samyak.githubsdk.interfaces.SearchGithubRepositoryEndpointCallback
import com.samyak.githubsdk.models.GithubRepoModel
import com.samyak.githubsdk.models.GithubRepositorySchema
import com.samyak.githubsdk.networking.SearchGithubRepositoryNetworkEndPoint
import java.util.*

class GithubRepository(
    private val mSearchGithubRepositoryNetworkEndPoint: SearchGithubRepositoryNetworkEndPoint,
    private val githubSdkListener: GithubSdkListener
) {

    fun searchForGithubRepository(platform: String, organization: String) {
        mSearchGithubRepositoryNetworkEndPoint.searchGithubRepository(
            platform,
            organization,
            object :
                SearchGithubRepositoryEndpointCallback {

                override fun onGithubRepositoryFetchSucceeded(repositorySchemaList: List<GithubRepositorySchema>) {
                    notifySuccess(repositorySchemaList)
                }

                override fun onGithubRepositoryFetchFailed() {
                    notifyFailure()
                }

            })
    }

    private fun notifySuccess(githubRepositorySchemaList: List<GithubRepositorySchema>) {
        val githubRepoModels = ArrayList<GithubRepoModel>()
        for (grs in githubRepositorySchemaList) {
            githubRepoModels.add(
                GithubRepoModel(
                    grs.name,
                    grs.isPrivate,
                    grs.description,
                    grs.language
                )
            )
        }
        githubSdkListener.onSearchedGithubRepositoryFetchSuccess(githubRepoModels)

    }

    private fun notifyFailure() {
        githubSdkListener.onSearchedGithubRepositoryFetchFailed()
    }

}