# MobileAssignment

# GithubSdk

To use the SDK,you can download and import **[githubsdk.aar](https://drive.google.com/open?id=1knSQn-g-GByqLtw-WuecG5o8BCDYkkqT)** file in any application. You need to create an object of **GithubRepository**. Its constructor takes two parameters, first one is the instance of **SearchGithubRepositoryNetworkEndPoint** and second is an instance of GithubSdkListener.

    githubRepository = GithubRepository(getSearchGithubRepositoryNetworkEndPoint(), this@MainActivity);

Then you have to call the searchForGithubRepository function and pass platform and organization as a parameter.

    githubRepository.searchForGithubRepository("android", “rakutentech");

**SearchGithubRepositoryNetworkEndPoint** is responsible for fetching the data from the server. 

Currently, it uses AsyncTask and HttpUrlConnection to make HTTP request and get the data from server, but it can be replaced by other libraries or method without changing the implementation of other classes. It exposes a function **searchGithubRepository***, where it takes *platform*, *organisation* as a string and **SearchGithubRepositoryEndpointCallback** callback to notify about success or failure in fetching data.


There is a parsing class **SearchGithubRepositoryEndpointParser** used in Asynctask to parse the string response into the appropriate schema and returns a list of **GithubRepositorySchema (List<GithubRepositorySchema>)**;
    
**SearchGithubRepositoryEndpointParserTest.java has the unit test cases for the parsing class.**

SearchGithubRepositoryEndpointParser where it test for various cases. Here are the function, whose names are self explanatory

    1. parserTest_nullJSONString_throwException
    2. parserTest_emptyJSONString_throwsException
    3. parserTest_incorrectSampleJSONString_throwsException
    4. parserTest_incorrectSampleJSONStructureString_throwsException
    5. parserTest_correctSampleJsonStructureButMissingKeys_throwsException
    6. parserTest_incorrectSampleJSONIncorrectValuesString_throwsException
    7. parserTest_correctSampleJson_githubRepoListReturned
    8. parserTest_correctSampleJsonWithExtraData_githubRepoListReturned

To create JSONObject while testing, I have included **testImplementation ‘org.json:json:20180130'** in gradle file since JSONObject is an Android Class that can’t be initiated while doing unit testing because it is just a stub. 	
 



**GithubRepositorySchema** and **GithubRepoModel** are two models reflecting server’s API and internal business login respectively. Reason for having two sets of similar data structures is because even if server api changes, I wouldn’t have to propogate the changes to internal logic of application. GithubRepository class is responsible for converting it to GithubRepoModel before sending to its consumer. 

I have run the coverage test on the file **SearchGithubRepositoryEndpointParserTest** and it **covers 100%** of the code of **SearchGithubRepositoryEndpointParser class.**

You can download githubsdk.aar **[here](https://drive.google.com/open?id=1knSQn-g-GByqLtw-WuecG5o8BCDYkkqT)**

