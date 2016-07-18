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

import java.util.Map;
import java.util.Objects;

/**
 * Created by amajumdar on 2/2/15.
 */
public class ConnectorPartitionMetadata {
    private final String partitionId;
    private final StorageInfo storageInfo;
    private Map<String, String> metadata;
    private final AuditInfo auditInfo;

    public ConnectorPartitionMetadata(String partitionId,
            Map<String, String> metadata) {
        this(partitionId, null, metadata, null);
    }

    public ConnectorPartitionMetadata(String partitionId,
            StorageInfo storageInfo, Map<String, String> metadata) {
        this(partitionId, storageInfo, metadata, null);
    }

    public ConnectorPartitionMetadata(String partitionId,
            StorageInfo storageInfo, Map<String, String> metadata, AuditInfo auditInfo) {
        this.partitionId = partitionId;
        this.storageInfo = storageInfo;
        this.metadata = metadata;
        this.auditInfo = auditInfo!=null?auditInfo:new AuditInfo();
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public StorageInfo getStorageInfo() {
        return storageInfo;
    }

    public String getPartitionId() {
        return partitionId;
    }

    public AuditInfo getAuditInfo() {
        return auditInfo;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    @Override
    public int hashCode() {
        return Objects.hash(partitionId, storageInfo, metadata, auditInfo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConnectorPartitionMetadata)) return false;
        ConnectorPartitionMetadata partitionMetadata = (ConnectorPartitionMetadata) o;
        return Objects.equals(partitionId, partitionMetadata.partitionId) &&
                Objects.equals(storageInfo, partitionMetadata.storageInfo) &&
                Objects.equals(metadata, partitionMetadata.metadata) &&
                Objects.equals(auditInfo, partitionMetadata.auditInfo);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ConnectorPartitionMetadata{");
        sb.append("partitionId='").append(partitionId).append('\'');
        sb.append(", storageInfo=").append(storageInfo);
        sb.append(", metadata=").append(metadata);
        sb.append(", auditInfo=").append(auditInfo);
        sb.append('}');
        return sb.toString();
    }
}
