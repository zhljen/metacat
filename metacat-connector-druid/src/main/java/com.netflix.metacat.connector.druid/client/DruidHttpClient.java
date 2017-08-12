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


package com.netflix.metacat.connector.druid.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.netflix.metacat.common.json.MetacatJsonLocator;
import com.netflix.metacat.common.server.connectors.ConnectorContext;
import com.netflix.metacat.connector.druid.DruidConfigConstants;
import com.netflix.metacat.connector.druid.IMetacatDruidClient;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * DruidHttpClient.
 *
 * @author zhenl
 * @since 1.1.0
 */
@Slf4j
public class DruidHttpClient implements IMetacatDruidClient {
    private String druidURI;
    private final RestTemplate restTemplate;
    private final MetacatJsonLocator jsonLocator = new MetacatJsonLocator();

    /**
     * Constructor.
     *
     * @param connectorContext connector context
     * @param restTemplate     rest template
     */
    public DruidHttpClient(final ConnectorContext connectorContext,
                           final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        final String host = connectorContext.getConfiguration().get(DruidConfigConstants.DRUID_SERVER);
        final String port = connectorContext.getConfiguration().get(DruidConfigConstants.DRUID_PORT);
        if (host != null && port != null) {
            this.druidURI = "http://" + host + ":" + port + DruidConfigConstants.DRUID_COORDINATOR_ENDPOINT;
            log.info("druid server uri={}", this.druidURI);
        }
    }

    /**
     * Get all data sources.
     *
     * @return data source names
     */
    @Override
    public List<String> getAllDataSources() {
        final JSONArray arr = new JSONArray(restTemplate.getForObject(druidURI, String.class));
        return IntStream.range(0, arr.length()).mapToObj(i -> arr.get(i).toString()).collect(Collectors.toList());
    }

    /**
     * Returns the data source.
     *
     * @param dataSourceName dataSourceName
     * @return list of tables
     */
    @Nullable
    @Override
    public Map<String, String> getDataSourceByName(final String dataSourceName) {
        final String result = restTemplate.getForObject(
            druidURI + "/{datasoureName}?full", String.class, dataSourceName);
        final ObjectNode node = jsonLocator.parseJsonObject(result);
        if (node != null) {
            final JsonNode segment = node.get(DruidConfigConstants.SEGMENTS)
                .get(node.get(DruidConfigConstants.SEGMENTS).size() - 1);
            final Map<String, String> ret = new HashMap<>();
            ret.put(DruidConfigConstants.DIMENSIONS, segment.get(DruidConfigConstants.DIMENSIONS).textValue());
            ret.put(DruidConfigConstants.METRICS, segment.get(DruidConfigConstants.METRICS).textValue());
            return ret;
        }
        return null;
    }
}
