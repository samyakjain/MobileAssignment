package com.samyak.githubsdk.networking

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.util.Log
import com.samyak.githubsdk.interfaces.SearchGithubRepositoryEndpointCallback
import com.samyak.githubsdk.models.GithubRepositorySchema
import com.samyak.githubsdk.parser.SearchGithubRepositoryEndpointParser
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class SearchGithubRepositoryNetworkEndPoint{

    private val APIENDPOINT = "https://api.github.com/search/repositories?q=%s+org:%s"

    fun searchGithubRepository(platform: String, organization: String, callback: SearchGithubRepositoryEndpointCallback) {
        val url = String.format(APIENDPOINT, platform, organization)
        SearchGithubAsync(callback).execute(url)
    }

    private inner class SearchGithubAsync(private var delegate: SearchGithubRepositoryEndpointCallback) : AsyncTask<String, Void, List<GithubRepositorySchema>>() {
        private var parser: SearchGithubRepositoryEndpointParser =
            SearchGithubRepositoryEndpointParser()

        override fun doInBackground(vararg strings: String): List<GithubRepositorySchema>? {
            var body = ""
            var httpURLConnection: HttpURLConnection? = null
            var result: List<GithubRepositorySchema>? = null
            try {
                val url = URL(strings[0])
                httpURLConnection = url.openConnection() as HttpURLConnection
                val inputStream = httpURLConnection.inputStream

                val streamReader = InputStreamReader(httpURLConnection.inputStream)
                val reader = BufferedReader(streamReader)
                val stringBuilder = StringBuilder()
                var inputLine: String
                for(line in reader.readLine()){
                    stringBuilder.append(line)
                }
                reader.close()
                streamReader.close()
                body = stringBuilder.toString()
                Log.i("SearchGithubAsync-Body", body)
                result = parser.parseAndReturnGithubRepositorySearchResponse(body)

            } catch (e: Exception) {
                Log.d("SearchGithubAsync", e.toString())
                delegate.onGithubRepositoryFetchFailed()
            } finally {
                httpURLConnection?.disconnect()
                return result
            }
        }

        override fun onPostExecute(repositorySchemas: List<GithubRepositorySchema>) {
            Log.i("Parsed Response", repositorySchemas.toString())
            delegate.onGithubRepositoryFetchSucceeded(repositorySchemas)
        }
    }



}