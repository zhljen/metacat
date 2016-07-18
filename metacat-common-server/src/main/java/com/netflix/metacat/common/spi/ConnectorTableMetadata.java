/*
 * Copyright 2016 Netflix, Inc.
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.netflix.metacat.common.spi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyMap;

/**
 * Created by amajumdar on 7/11/16.
 */
public class ConnectorTableMetadata
{
    private final SchemaTableName table;
    private final List<ColumnMetadata> columns;
    private final Map<String, Object> properties;
    /* nullable */
    private final String owner;
    private final boolean sampled;
    private StorageInfo storageInfo;
    private Map<String, String> metadata;
    private AuditInfo auditInfo;

    public ConnectorTableMetadata(SchemaTableName table, List<ColumnMetadata> columns)
    {
        this(table, columns, emptyMap());
    }

    public ConnectorTableMetadata(SchemaTableName table, List<ColumnMetadata> columns, Map<String, Object> properties)
    {
        this(table, columns, properties, null);
    }

    public ConnectorTableMetadata(SchemaTableName table, List<ColumnMetadata> columns, Map<String, Object> properties, String owner)
    {
        this(table, columns, properties, owner, false, null, null, null);
    }

    public ConnectorTableMetadata(SchemaTableName table, List<ColumnMetadata> columns, Map<String, Object> properties
            , String owner, boolean sampled
            , StorageInfo storageInfo, Map<String, String> metadata
            , AuditInfo auditInfo)
    {
        if (table == null) {
            throw new NullPointerException("table is null or empty");
        }
        if (columns == null) {
            throw new NullPointerException("columns is null");
        }

        this.table = table;
        this.columns = Collections.unmodifiableList(new ArrayList<>(columns));
        this.properties = Collections.unmodifiableMap(new LinkedHashMap<>(properties));
        this.owner = owner;
        this.sampled = sampled;
        this.storageInfo = storageInfo;
        this.metadata = metadata;
        this.auditInfo = auditInfo!=null?auditInfo:new AuditInfo();
    }

    public boolean isSampled()
    {
        return sampled;
    }

    public SchemaTableName getTable()
    {
        return table;
    }

    public List<ColumnMetadata> getColumns()
    {
        return columns;
    }

    public Map<String, Object> getProperties()
    {
        return properties;
    }

    public StorageInfo getStorageInfo() {
        return storageInfo;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public AuditInfo getAuditInfo() {
        return auditInfo;
    }

    /**
     * @return table owner or null
     */
    public String getOwner()
    {
        return owner;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ConnectorTableMetadata{");
        sb.append("table=").append(table);
        sb.append(", columns=").append(columns);
        sb.append(", properties=").append(properties);
        sb.append(", owner='").append(owner).append('\'');
        sb.append(", sampled=").append(sampled);
        sb.append(", storageInfo=").append(storageInfo);
        sb.append(", metadata=").append(metadata);
        sb.append(", auditInfo=").append(auditInfo);
        sb.append('}');
        return sb.toString();
    }
}
