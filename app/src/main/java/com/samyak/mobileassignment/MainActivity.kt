package com.samyak.mobileassignment

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.samyak.githubsdk.GithubRepository
import com.samyak.githubsdk.interfaces.GithubSdkListener
import com.samyak.githubsdk.models.GithubRepoModel
import com.samyak.githubsdk.networking.SearchGithubRepositoryNetworkEndPoint

class MainActivity : AppCompatActivity(), GithubSdkListener {

    private var githubRepository: GithubRepository? = null
    private var searchGithubRepositoryNetworkEndPoint: SearchGithubRepositoryNetworkEndPoint? = null

    fun getSearchGithubRepositoryNetworkEndPoint(): SearchGithubRepositoryNetworkEndPoint {
        if (searchGithubRepositoryNetworkEndPoint == null) {
            searchGithubRepositoryNetworkEndPoint = SearchGithubRepositoryNetworkEndPoint()
        }
        return searchGithubRepositoryNetworkEndPoint as SearchGithubRepositoryNetworkEndPoint
    }

    fun getGithubRepository(): GithubRepository {
        if (githubRepository == null) {
            githubRepository =
                GithubRepository(getSearchGithubRepositoryNetworkEndPoint(), this@MainActivity);
        }
        return githubRepository as GithubRepository
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getGithubRepository().searchForGithubRepository("android", "rakutentech");
    }

    override fun onSearchedGithubRepositoryFetchSuccess(githubRepoModels: List<GithubRepoModel>) {
        Log.i("MainActivity", githubRepoModels.toString());
    }

    override fun onSearchedGithubRepositoryFetchFailed() {
        Log.i("MainActivity", "Searching Repo Failed");
    }
}
