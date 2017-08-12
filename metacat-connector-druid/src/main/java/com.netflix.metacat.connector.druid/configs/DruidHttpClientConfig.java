/*
 *  Copyright 2017 Netflix, Inc.
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package com.netflix.metacat.connector.druid.configs;

import com.netflix.metacat.common.server.connectors.ConnectorContext;
import com.netflix.metacat.connector.druid.IMetacatDruidClient;
import com.netflix.metacat.connector.druid.client.DruidHttpClient;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * DruidHttpClientConfig.
 *
 * @author zhenl
 * @since 1.1.0
 */
@Configuration
@ConditionalOnProperty(value = "httpClient", havingValue = "true")
public class DruidHttpClientConfig {
    /**
     * Druid client instance.
     * @param connectorContext connector context
     * @param restTemplate rest template
     * @return IMetacatDruidClient
     */
    @Bean
    public IMetacatDruidClient createMetacatDruidClient(
        final ConnectorContext connectorContext,
        final RestTemplate restTemplate) {
        return new DruidHttpClient(connectorContext, restTemplate);
    }
    /**
     * rest template.
     *
     * @param connectorContext   connector context
     * @return RestTemplate
     */
    @Bean
    public RestTemplate restTemplate(final ConnectorContext connectorContext) {
        return new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient(connectorContext)));
    }

    /**
     * http client.
     *
     * @param connectorContext connector context
     * @return HttpClient
     */
    @Bean
    public HttpClient httpClient(final ConnectorContext connectorContext) {
        final int timeout = 5000;
        final RequestConfig config = RequestConfig.custom()
            .setConnectTimeout(timeout) //to config in the connector configuration
            .setConnectionRequestTimeout(timeout)
            .setSocketTimeout(timeout)
            .setMaxRedirects(3)
            .build();

        final PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();

        // Get the poolMaxTotal value from our application[-?].yml or default to 10 if not explicitly set
        //  connectionManager.setMaxTotal(environment.getProperty("poolMaxTotal", Integer.class, 10));
        connectionManager.setMaxTotal(10);
        return HttpClientBuilder
            .create()
            .setDefaultRequestConfig(config)
            .setConnectionManager(connectionManager)
            .build();
    }
}
