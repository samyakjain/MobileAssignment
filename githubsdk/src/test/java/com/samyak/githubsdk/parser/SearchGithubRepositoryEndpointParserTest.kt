package com.samyak.githubsdk.parser

import com.samyak.githubsdk.models.GithubRepositorySchema
import org.hamcrest.CoreMatchers
import org.json.JSONException
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.lang.Boolean.FALSE

class SearchGithubRepositoryEndpointParserTest {

    private val NAME: String = "name"
    private val DESCRIPTION: String ="description"
    private val LANGUAGE: String ="language"
    private val FORKS: Int = 100
    private val OPENISSUES: Int? = 101
    private val WATCHERS: Int? = 102

    private lateinit var SUT: SearchGithubRepositoryEndpointParser

    @Before
    fun setUp() {
        SUT = SearchGithubRepositoryEndpointParser()
    }

    @Test(expected = NullPointerException::class)
    fun parserTest_nullJSONString_throwException() {
        SUT.parseAndReturnGithubRepositorySearchResponse(null)
    }

    @Test(expected = JSONException::class)
    fun parserTest_emptyJSONString_throwsException() {
        SUT.parseAndReturnGithubRepositorySearchResponse("")
    }

    @Test(expected = ParsingException::class)
    fun parserTest_incorrectSampleJSONString_throwsException() {
        val incorrectMockJsonString = incorrectMockJsonString()
        SUT.parseAndReturnGithubRepositorySearchResponse(incorrectMockJsonString)
    }

    @Test(expected = JSONException::class)
    fun parserTest_incorrectSampleJSONStructureString_throwsException() {
        val incorrectMockJsonString = incorrectMockJsonStructureCommaMissingString()
        SUT.parseAndReturnGithubRepositorySearchResponse(incorrectMockJsonString)
    }

    @Test(expected = ParsingException::class)
    fun parserTest_correctSampleJsonStructureButMissingKeys_throwsException() {
        val correctMockJsonString = incorrectMockJsonStructureDueToMissingKeyString()
        SUT.parseAndReturnGithubRepositorySearchResponse(correctMockJsonString)
    }

    @Test(expected = JSONException::class)
    fun parserTest_incorrectSampleJSONIncorrectValuesString_throwsException() {
        val incorrectMockJsonString = incorrectMockJsonIncorrectDataType()
        SUT.parseAndReturnGithubRepositorySearchResponse(incorrectMockJsonString)
    }

    @Test
    fun parserTest_correctSampleJson_githubRepoListReturned() {
        val correctMockJsonString = correctMockJsonStructureAndDataString()
        val githubRepositorySchemaList =
            SUT.parseAndReturnGithubRepositorySearchResponse(correctMockJsonString)
        doAssertionForCorrectParsedResponse(githubRepositorySchemaList)
    }

    @Test
    fun parserTest_correctSampleJsonWithExtraData_githubRepoListReturned() {
        val correctMockJsonString = correctMockJsonStructureWithExtraDataString()
        val githubRepositorySchemaList =
            SUT.parseAndReturnGithubRepositorySearchResponse(correctMockJsonString)
        doAssertionForCorrectParsedResponse(githubRepositorySchemaList)
    }

    private fun doAssertionForCorrectParsedResponse(githubRepositorySchemaList: List<GithubRepositorySchema>) {
        Assert.assertNotNull(githubRepositorySchemaList)
        Assert.assertTrue(githubRepositorySchemaList.isNotEmpty())
        val githubRepositorySchema = githubRepositorySchemaList[0]
        Assert.assertThat(
            githubRepositorySchema,
            CoreMatchers.`is`(
                GithubRepositorySchema(
                    NAME,
                    FALSE,
                    DESCRIPTION,
                    LANGUAGE,
                    FORKS,
                    OPENISSUES,
                    WATCHERS
                )
            )
        )
    }

    private fun incorrectMockJsonString(): String {
        return "{'incorrectJson':true}"
    }

    private fun incorrectMockJsonStructureCommaMissingString(): String {
        return "{\n" +
                "  \"items\":[\n" +
                "    {\n" +
                "      \"name\":\"name\"\n" +
                "      \"description\":\"description\",\n" +
                "      \"private\":false,\n" +
                "      \"language\":\"language\",\n" +
                "    }\n" +
                "  ]\n" +
                "}"
    }

    private fun incorrectMockJsonStructureDueToMissingKeyString(): String {
        //watchers,forks_count,openIssus is missing
        return "{\n" +
                "  \"items\":[\n" +
                "    {\n" +
                "      \"name\":\"name\",\n" +
                "      \"description\":\"description\",\n" +
                "      \"private\":false,\n" +
                "      \"language\":\"language\",\n" +
                "    }\n" +
                "  ]\n" +
                "}"
    }

    private fun incorrectMockJsonIncorrectDataType(): String {
        return "{\n" +
                "  \"items\":[\n" +
                "    {\n" +
                "      \"name\":819517,\n" +
                "      \"description\":\"description\",\n" +
                "      \"private\":false,\n" +
                "      \"language\":\"language\",\n" +
                "      \"forks_count\":100,\n" +
                "      \"open_issues\":101,\n" +
                "      \"watchers\":102,\n" +
                "      \"dummy1\":\"data1\",\n" +
                "      \"dummy2\":2988732\n" +
                "    }\n" +
                "  ]\n" +
                "}"
    }

    private fun correctMockJsonStructureAndDataString(): String {
        return "{\n" +
                "  \"items\":[\n" +
                "    {\n" +
                "      \"name\":\"name\",\n" +
                "      \"description\":\"description\",\n" +
                "      \"private\":false,\n" +
                "      \"language\":\"language\",\n" +
                "      \"forks_count\":100,\n" +
                "      \"open_issues\":101,\n" +
                "      \"watchers\":102\n" +
                "    }\n" +
                "  ]\n" +
                "}"
    }

    private fun correctMockJsonStructureWithExtraDataString(): String {
        return "{\n" +
                "  \"items\":[\n" +
                "    {\n" +
                "      \"name\":\"name\",\n" +
                "      \"description\":\"description\",\n" +
                "      \"private\":false,\n" +
                "      \"language\":\"language\",\n" +
                "      \"forks_count\":100,\n" +
                "      \"open_issues\":101,\n" +
                "      \"watchers\":102,\n" +
                "      \"dummy1\":\"data1\",\n" +
                "      \"dummy2\":2988732\n" +
                "    }\n" +
                "  ]\n" +
                "}"
    }

}