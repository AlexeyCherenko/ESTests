package com.epam.elasticsearchDataExtract

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import org.apache.http.HttpHost
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.builder.SearchSourceBuilder

class DataExtractor {

    private static final String LOCAL_HOST = "localhost"
    private static final int PORT = 9200
    private static final String PROTOCOL = "http"

     static void main(String[] args){

         def responseObj = extractDataFromES()

         def values = valuesForProvidedKey(responseObj.hits.hits._source, 'FirstName')
         println values

         def objects = objectsWhereKeyAppears(responseObj.hits.hits, '_source', 3)
         println objects
    }

    static def valuesForProvidedKey(def responseObj, String  key){
        responseObj[key]
    }

    static def objectsWhereKeyAppears(def responseObj, String key, def serialNumber){
        responseObj[key][serialNumber]
    }

    static def extractDataFromES(){

        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
                new HttpHost(LOCAL_HOST, PORT, PROTOCOL)))

        SearchRequest searchRequest = new SearchRequest()
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
        searchSourceBuilder.query(QueryBuilders.matchAllQuery())
        searchRequest.source(searchSourceBuilder)
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT)

        def responseObj = new JsonSlurper().parseText(response.toString())

        println "---------------------- " + "\n" +
                "-- Response from ES -- " + "\n" +
                "----------------------"
        println JsonOutput.prettyPrint(response.toString())

        responseObj
    }

}
