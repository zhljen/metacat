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

package com.netflix.metacat.connector.druid;

import com.netflix.metacat.common.server.connectors.ConnectorContext;
import com.netflix.metacat.common.server.connectors.ConnectorDatabaseService;
import com.netflix.metacat.common.server.connectors.ConnectorFactory;
import com.netflix.metacat.common.server.connectors.ConnectorTableService;
import com.netflix.metacat.connector.druid.configs.DruidConnectorConfig;
import com.netflix.metacat.connector.druid.configs.DruidHttpClientConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.StandardEnvironment;

import java.util.HashMap;
import java.util.Map;

/**
 * Druid Connector Factory.
 * @author zhenl
 * @since 1.1.0
 */
public class DruidConnectorFactory implements ConnectorFactory {
    private final String catalogName;
    private AnnotationConfigApplicationContext ctx;

    /**
     * Constructor.
     *
     * @param catalogName      connector name. Also the catalog name.
     * @param connectorContext connector config
     */
    DruidConnectorFactory(
        final String catalogName,
        final ConnectorContext connectorContext
    ) {
        this.catalogName = catalogName;
        this.ctx = new AnnotationConfigApplicationContext();
        this.ctx.getBeanFactory().registerSingleton("ConnectorContext", connectorContext);
        final StandardEnvironment standardEnvironment = new StandardEnvironment();
        final MutablePropertySources propertySources = standardEnvironment.getPropertySources();
        final Map<String, Object> properties = new HashMap<>();
        properties.put("httpClient", true);
        propertySources.addFirst(new MapPropertySource("DRUID_CONNECTOR", properties));
        this.ctx.setEnvironment(standardEnvironment);
        this.ctx.register(DruidConnectorConfig.class);
        this.ctx.register(DruidHttpClientConfig.class);
        this.ctx.refresh();
    }
    /**
     * Returns the database service implementation of the connector.
     *
     * @return Returns the database service implementation of the connector.
     */
    @Override
    public ConnectorDatabaseService getDatabaseService() {
        return this.ctx.getBean(DruidConnectorDatabaseService.class);
    }

    /**
     * Returns the table service implementation of the connector.
     *
     * @return Returns the table service implementation of the connector.
     */
    @Override
    public ConnectorTableService getTableService() {
        return this.ctx.getBean(DruidConnectorTableService.class);
    }

    /**
     * Returns the name of the connector.
     *
     * @return Returns the name of the connector.
     */
    @Override
    public String getName() {
        return this.catalogName;
    }

    /**
     * Shuts down the factory.
     */
    @Override
    public void stop() {
    }
}
