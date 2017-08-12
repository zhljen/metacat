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

import com.google.common.collect.Lists;
import com.netflix.metacat.common.QualifiedName;
import com.netflix.metacat.common.dto.Pageable;
import com.netflix.metacat.common.dto.Sort;
import com.netflix.metacat.common.server.connectors.ConnectorRequestContext;
import com.netflix.metacat.common.server.connectors.ConnectorTableService;
import com.netflix.metacat.common.server.connectors.ConnectorUtils;
import com.netflix.metacat.common.server.connectors.exception.ConnectorException;
import com.netflix.metacat.common.server.connectors.model.FieldInfo;
import com.netflix.metacat.common.server.connectors.model.TableInfo;
import com.netflix.metacat.common.type.BaseType;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Druid Connector Table Service.
 *
 * @author zhenl
 * @since 1.1.0
 */
@Slf4j
public class DruidConnectorTableService implements ConnectorTableService {
    private final IMetacatDruidClient druidClient;

    /**
     * Constructor.
     *
     * @param druidClient druid client
     */
    public DruidConnectorTableService(
        final IMetacatDruidClient druidClient
    ) {
        this.druidClient = druidClient;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TableInfo get(@Nonnull final ConnectorRequestContext context, @Nonnull final QualifiedName name) {
        log.debug("Beginning to get table metadata for qualified name {} for request {}", name, context);
        final Map<String, String> segInfo = this.druidClient.getDataSourceByName(name.getTableName());
        final List<FieldInfo> fieldInfos = new ArrayList<>();
        fieldInfos.add(FieldInfo.builder()
            .name(DruidConfigConstants.DIMENSIONS)
            .type(BaseType.STRING)
            .defaultValue(segInfo.get(DruidConfigConstants.DIMENSIONS))
            .build());
        fieldInfos.add(FieldInfo.builder()
            .name(DruidConfigConstants.METRICS)
            .type(BaseType.STRING)
            .defaultValue(segInfo.get(DruidConfigConstants.METRICS))
            .build());
        return TableInfo.builder().fields(fieldInfos).build();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<QualifiedName> listNames(
        final ConnectorRequestContext requestContext,
        final QualifiedName name,
        @Nullable final QualifiedName prefix,
        @Nullable final Sort sort,
        @Nullable final Pageable pageable
    ) {
        try {
            final List<QualifiedName> qualifiedNames = Lists.newArrayList();

            final String tableFilter = (prefix != null && prefix.isTableDefinition()) ? prefix.getTableName() : null;
            for (String tableName : this.druidClient.getAllDataSources()) {
                if (tableFilter == null || tableName.startsWith(tableFilter)) {
                    final QualifiedName qualifiedName =
                        QualifiedName.ofTable(name.getCatalogName(), name.getDatabaseName(), tableName);
                    if (prefix != null && !qualifiedName.toString().startsWith(prefix.toString())) {
                        continue;
                    }
                    qualifiedNames.add(qualifiedName);
                }
            }
            if (sort != null) {
                ConnectorUtils.sort(qualifiedNames, sort, Comparator.comparing(QualifiedName::toString));
            }
            return ConnectorUtils.paginate(qualifiedNames, pageable);
        } catch (Exception exception) {
            throw new ConnectorException(String.format("Failed listNames druid table %s", name), exception);
        }
    }
}
