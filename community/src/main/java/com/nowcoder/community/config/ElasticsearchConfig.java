//package com.nowcoder.community.config;
//
//import org.apache.http.HttpHost;
//import org.elasticsearch.client.RestClient;
////import org.elasticsearch.client.RestHighLevelClient;
////import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
//
//@Configuration
//public class ElasticsearchConfig {
//
//    @Bean
//    public RestHighLevelClient client() {
//        return new RestHighLevelClient(
//                RestClient.builder(new HttpHost("localhost", 9200, "http"))
//        );
//    }
//
//    @Bean
//    public ElasticsearchRestTemplate elasticsearchTemplate(RestHighLevelClient client) {
//        return new ElasticsearchRestTemplate(client);
//    }
//}