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

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by amajumdar on 2/2/15.
 */
public interface ConnectorSplitDetailManager{
    /**
     * Gets the Partitions based on a filter expression for the specified table.
     * @param tableName table name
     * @param filterExpression JSP based filter expression string
     * @param partitionNames filter the list that matches the given partition names. If null or empty, it will return all.
     * @return filtered list of partitions
     */
    List<ConnectorPartitionMetadata> getPartitions(SchemaTableName tableName, String filterExpression,
            List<String> partitionNames, Sort sort, Pageable pageable, boolean includePartitionDetails);

    /**
     * Add/Update/delete partitions for a table
     * @param tableName table name
     * @param partitions list of partitions
     * @param partitionIdsForDeletes list of partition ids/names for deletes
     * @return added/updated list of partition names
     */
    SavePartitionResult savePartitions(SchemaTableName tableName, List<ConnectorPartitionMetadata> partitions,
            List<String> partitionIdsForDeletes,
            boolean checkIfExists, boolean alterIfExists);

    /**
     * Delete partitions for a table
     * @param tableName table name
     * @param partitionIds list of partition names
     */
    void deletePartitions(SchemaTableName tableName, List<String> partitionIds);

    /**
     * Number of partitions for the given table
     * @param tableName table name
     * @return Number of partitions
     */
    Integer getPartitionCount(SchemaTableName tableName);

    /**
     * Returns all the partition names referring to the given <code>uri</code>
     * @param uri location
     * @param prefixSearch if tru, we look for tables whose location starts with the given <code>uri</code>
     * @return list of partition names
     */
    default List<SchemaTablePartitionName> getPartitionNames(String uri, boolean prefixSearch){
        return Lists.newArrayList();
    }

    /**
     * Gets the partition names/keys based on a filter expression for the specified table.
     * @param tableName table name
     * @param filterExpression JSP based filter expression string
     * @param partitionNames filter the list that matches the given partition names. If null or empty, it will return all.
     * @return filtered list of partition names
     */
    default List<String> getPartitionKeys(SchemaTableName tableName, String filterExpression,
            List<String> partitionNames, Sort sort, Pageable pageable){
        return Lists.newArrayList();
    }

    /**
     * Gets the partition uris based on a filter expression for the specified table.
     * @param tableName table name
     * @param filterExpression JSP based filter expression string
     * @param partitionNames filter the list that matches the given partition names. If null or empty, it will return all.
     * @return filtered list of partition uris
     */
    default List<String> getPartitionUris(SchemaTableName tableName , String filterExpression,
            List<String> partitionNames, Sort sort, Pageable pageable){
        return Lists.newArrayList();
    }
}
