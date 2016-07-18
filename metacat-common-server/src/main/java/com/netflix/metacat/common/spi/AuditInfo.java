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

import java.util.Objects;

/**
 * Created by amajumdar on 3/3/15.
 */
public class AuditInfo {
    private String createdBy;
    private String lastUpdatedBy;
    private Long createdDate;
    private Long lastUpdatedDate;

    public AuditInfo() {
    }

    public AuditInfo(String createdBy, String lastUpdatedBy, Long createdDate, Long lastUpdatedDate) {
        this.createdBy = createdBy;
        this.lastUpdatedBy = lastUpdatedBy;
        this.createdDate = createdDate;
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }

    public Long getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Long lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(createdBy, lastUpdatedBy, createdDate, lastUpdatedDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuditInfo)) return false;
        AuditInfo auditInfo = (AuditInfo) o;
        return Objects.equals(createdBy, auditInfo.createdBy) &&
                Objects.equals(lastUpdatedBy, auditInfo.lastUpdatedBy) &&
                Objects.equals(createdDate, auditInfo.createdDate) &&
                Objects.equals(lastUpdatedDate, auditInfo.lastUpdatedDate);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AuditInfo{");
        sb.append("createdBy='").append(createdBy).append('\'');
        sb.append(", lastUpdatedBy='").append(lastUpdatedBy).append('\'');
        sb.append(", createdDate=").append(createdDate);
        sb.append(", lastUpdatedDate=").append(lastUpdatedDate);
        sb.append('}');
        return sb.toString();
    }
}
