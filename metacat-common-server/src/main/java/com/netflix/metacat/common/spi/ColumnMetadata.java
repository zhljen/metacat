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

import com.netflix.metacat.common.type.Type;

import java.util.Objects;

import static java.util.Locale.ENGLISH;

/**
 * Created by amajumdar on 9/28/15.
 */
public class ColumnMetadata {
    private final String name;
    private final Type type;
    private final boolean partitionKey;
    private final String comment;
    private final boolean hidden;
    private final String sourceType;
    private final Integer size;
    private final Boolean isNullable;
    private final String defaultValue;
    private final Boolean isSortKey;
    private final Boolean isIndexKey;

    public ColumnMetadata(String name, Type type, boolean partitionKey, String sourceType) {
        this(name , type, partitionKey, null, false, sourceType, null, null, null, null, null);
    }

    public ColumnMetadata(String name, Type type, boolean partitionKey, String comment, boolean hidden
            , String sourceType) {
        this(name , type, partitionKey, comment, hidden, sourceType, null, null, null, null, null);
    }

    public ColumnMetadata(String name, Type type, boolean partitionKey, String comment, boolean hidden
            , String sourceType, Integer size, Boolean isNullable
            , String defaultValue, Boolean isSortKey, Boolean isIndexKey) {
        if (name == null || name.isEmpty()) {
            throw new NullPointerException("name is null or empty");
        }
        if (type == null) {
            throw new NullPointerException("type is null");
        }

        this.name = name.toLowerCase(ENGLISH);
        this.type = type;
        this.partitionKey = partitionKey;
        this.comment = comment;
        this.hidden = hidden;
        this.sourceType = sourceType;
        this.size = size;
        this.isNullable = isNullable;
        this.defaultValue = defaultValue;
        this.isSortKey = isSortKey;
        this.isIndexKey = isIndexKey;
    }

    public String getName()
    {
        return name;
    }

    public Type getType()
    {
        return type;
    }

    public boolean isPartitionKey()
    {
        return partitionKey;
    }

    public String getComment()
    {
        return comment;
    }

    public boolean isHidden()
    {
        return hidden;
    }

    public String getSourceType() {
        return sourceType;
    }

    public Boolean getIsNullable() {
        return isNullable;
    }

    public Integer getSize() {
        return size;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public Boolean getIsSortKey() {
        return isSortKey;
    }

    public Boolean getIsIndexKey() {
        return isIndexKey;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, partitionKey, comment, hidden, sourceType, size, isNullable, defaultValue, isSortKey, isIndexKey);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ColumnMetadata)) return false;
        ColumnMetadata cdm = (ColumnMetadata) o;
        return Objects.equals(name, cdm.name) &&
                Objects.equals(type, cdm.type) &&
                Objects.equals(partitionKey, cdm.partitionKey) &&
                Objects.equals(comment, cdm.comment) &&
                Objects.equals(hidden, cdm.hidden) &&
                Objects.equals(sourceType, cdm.sourceType) &&
                Objects.equals(size, cdm.size) &&
                Objects.equals(isNullable, cdm.isNullable) &&
                Objects.equals(defaultValue, cdm.defaultValue) &&
                Objects.equals(isSortKey, cdm.isSortKey) &&
                Objects.equals(isIndexKey, cdm.isIndexKey);
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder("ColumnDetailMetadata{");
        sb.append("name='").append(name).append('\'');
        sb.append(", type=").append(type);
        sb.append(", partitionKey=").append(partitionKey);
        if (comment != null) {
            sb.append(", comment='").append(comment).append('\'');
        }
        if (hidden) {
            sb.append(", hidden");
        }
        sb.append('}');
        return sb.toString();
    }
}
