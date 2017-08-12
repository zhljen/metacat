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
import com.netflix.metacat.common.server.util.DataSourceManager;
import com.netflix.metacat.connector.druid.DruidConnectorDatabaseService;
import com.netflix.metacat.connector.druid.DruidConnectorTableService;
import com.netflix.metacat.connector.druid.IMetacatDruidClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Druid Connector Config.
 *
 * @author zhenl
 * @since 1.1.0
 */
@Configuration
public class DruidConnectorConfig {
    /**
     * Druid DataSource.
     *
     * @param connectorContext connector config.
     * @return data source
     */
    @Bean
    @ConditionalOnMissingBean(IMetacatDruidClient.class)
    public DataSource druidDataSource(final ConnectorContext connectorContext) {
        DataSourceManager.get().load(
            connectorContext.getCatalogName(),
            connectorContext.getConfiguration()
        );
        return DataSourceManager.get().get(connectorContext.getCatalogName());
    }

    /**
     * create druid connector table service.
     *
     * @param druidClient druid Client
     * @return druid connector table Service
     */
    @Bean
    public DruidConnectorTableService druidTableService(
        final IMetacatDruidClient druidClient) {
        return new DruidConnectorTableService(
            druidClient
        );
    }

    /**
     * create druid connector database service.
     *
     * @return druid connector database Service
     */
    @Bean
    public DruidConnectorDatabaseService druidDatabaseService() {
        return new DruidConnectorDatabaseService();
    }


}
