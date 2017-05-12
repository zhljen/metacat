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

package com.netflix.metacat.common.server.monitoring;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

//CHECKSTYLE:OFF
/**
 * Log constants.
 */
public enum LogConstants {
    /**
     * General logging constants.
     */
    AppPrefix("metacat"),
    /**
     * Catalog, database, table operation counters.
     */
    CounterCreateCatalog(AppPrefix + ".countCreateCatalog"),
    CounterCreateTable(AppPrefix + ".countCreateTable"),
    CounterCreateDatabase(AppPrefix + ".countCreateDatabase"),
    CounterCreateMView(AppPrefix + ".countCreateMView"),
    CounterDeleteDatabase(AppPrefix + ".countDeleteDatabase"),
    CounterDeleteTablePartitions(AppPrefix + ".countDeleteTablePartitions"),
    CounterDeleteMViewPartitions(AppPrefix + ".countDeleteMViewPartitions"),
    CounterDeleteTable(AppPrefix + ".countDropTable"),
    CounterDeleteMView(AppPrefix + ".countDeleteMView"),
    CounterGetCatalog(AppPrefix + ".countGetMetadata"),
    CounterGetCatalogNames(AppPrefix + ".countGetCatalogNames"),
    CounterGetDatabase(AppPrefix + ".countGetDatabase"),
    CounterGetMViewPartitions(AppPrefix + ".countGetMViewPartitions"),
    CounterGetTablePartitions(AppPrefix + ".countGetTablePartitions"),
    CounterGetTable(AppPrefix + ".countGetTable"),
    CounterGetMView(AppPrefix + ".countGetMView"),
    CounterGetCatalogMViews(AppPrefix + ".countGetCatalogMViews"),
    CounterGetTableMViews(AppPrefix + ".countGetTableMViews"),
    CounterRenameTable(AppPrefix + ".countRenameTable"),
    CounterUpdateCatalog(AppPrefix + ".countUpdateCatalog"),
    CounterUpdateTable(AppPrefix + ".countUpdateTable"),
    CounterSaveTablePartitions(AppPrefix + ".countSaveTablePartitions"),
    CounterSaveMViewPartitions(AppPrefix + ".countSaveMViewPartitions"),
    CounterCreateCatalogFailure(AppPrefix + ".countCreateCatalogFailure"),
    CounterCreateTableFailure(AppPrefix + ".countCreateTableFailure"),
    CounterCreateDatabaseFailure(AppPrefix + ".countCreateDatabaseFailure"),
    CounterCreateMViewFailure(AppPrefix + ".countCreateMViewFailure"),
    CounterDeleteDatabaseFailure(AppPrefix + ".countDeleteDatabaseFailure"),
    CounterDeleteTablePartitionsFailure(AppPrefix + ".countDeleteTablePartitionsFailure"),
    CounterDeleteMViewPartitionsFailure(AppPrefix + ".countDeleteMViewPartitionsFailure"),
    CounterDeleteTableFailure(AppPrefix + ".countDropTableFailure"),
    CounterDeleteMViewFailure(AppPrefix + ".countDeleteMViewFailure"),
    CounterGetCatalogFailure(AppPrefix + ".countGetMetadataFailure"),
    CounterGetCatalogNamesFailure(AppPrefix + ".countGetCatalogNamesFailure"),
    CounterGetDatabaseFailure(AppPrefix + ".countGetDatabaseFailure"),
    CounterGetMViewPartitionsFailure(AppPrefix + ".countGetMViewPartitionsFailure"),
    CounterGetTablePartitionsFailure(AppPrefix + ".countGetTablePartitionsFailure"),
    CounterGetTableFailure(AppPrefix + ".countGetTableFailure"),
    CounterGetMViewFailure(AppPrefix + ".countGetMViewFailure"),
    CounterGetCatalogMViewsFailure(AppPrefix + ".countGetCatalogMViewsFailure"),
    CounterGetTableMViewsFailure(AppPrefix + ".countGetTableMViewsFailure"),
    CounterRenameTableFailure(AppPrefix + ".countRenameTableFailure"),
    CounterUpdateCatalogFailure(AppPrefix + ".countUpdateCatalogFailure"),
    CounterUpdateTableFailure(AppPrefix + ".countUpdateTableFailure"),
    CounterSaveTablePartitionsFailure(AppPrefix + ".countSaveTablePartitionsFailure"),
    CounterSaveMViewPartitionsFailure(AppPrefix + ".countSaveMViewPartitionsFailure"),

    /**
     * evnets.
     */
    CounterEventAsync(AppPrefix + ".events.Async"),
    CounterEventSync(AppPrefix + ".events.Sync"),

    /**
     * thrift request.
     */
    CounterThrift(AppPrefix + ".thrift.count"),

    /**
     * hive sql lock error.
     */
    CounterHiveSqlLockError(AppPrefix + ".hiveSqlLockError"),

    /**
     * metacat request.
     */
    CounterRequestCount(AppPrefix + ".requests.counter"),


    /**
     * Notifications.
     */
    CounterSNSNotificationPartitionAdd(AppPrefix + ".notifications.count.partitionsAdd"),
    CounterSNSNotificationTablePartitionAdd(AppPrefix + ".notifications.count.table.partitionsAdd"),
    CounterSNSNotificationPartitionDelete(AppPrefix + ".notifications.count.partitionsDelete"),
    CounterSNSNotificationTablePartitionDelete(AppPrefix + ".notifications.count.table.partitionsDelete"),
    CounterSNSNotificationTableCreate(AppPrefix + ".notifications.count.table.Create"),
    CounterSNSNotificationTableDelete(AppPrefix + ".notifications.count.table.Delete"),
    CounterSNSNotificationTableRename(AppPrefix + ".notifications.count.table.Rename"),
    CounterSNSNotificationTableUpdate(AppPrefix + ".notifications.count.table.Update"),

    /**
     * ElasticSearch.
     */
    CounterElasticSearchDatabaseCreate(AppPrefix + ".elasticsearch.count.databaseCreate"),
    CounterElasticSearchDatabaseDelete(AppPrefix + ".elasticsearch.count.databaseDelete"),
    CounterElasticSearchTableCreate(AppPrefix + ".elasticsearch.count.tableCreate"),
    CounterElasticSearchTableDelete(AppPrefix + ".elasticsearch.count.tableDelete"),
    CounterElasticSearchTableSave(AppPrefix + ".elasticsearch.count.tableSave"),
    CounterElasticSearchTableRename(AppPrefix + ".elasticsearch.count.tableRename"),
    CounterElasticSearchTableUpdate(AppPrefix + ".elasticsearch.count.tableUpdate"),
    CounterElasticSearchPartitionSave(AppPrefix + ".elasticsearch.count.partitionSave"),
    CounterElasticSearchPartitionDelete(AppPrefix + ".elasticsearch.count.partitionDelete"),
    CounterElasticSearchDelete(AppPrefix + ".elasticsearch.count.esDelete"),
    CounterElasticSearchBulkDelete(AppPrefix + ".elasticsearch.count.esBulkDelete"),
    CounterElasticSearchUpdate(AppPrefix + ".elasticsearch.count.esUpdate"),
    CounterElasticSearchBulkUpdate(AppPrefix + ".elasticsearch.count.esBulkUpdate"),
    CounterElasticSearchSave(AppPrefix + ".elasticsearch.count.esSave"),
    CounterElasticSearchBulkSave(AppPrefix + ".elasticsearch.count.esBulkSave"),
    CounterElasticSearchLog(AppPrefix + ".elasticsearch.count.esLog"),
    CounterElasticSearchRefresh(AppPrefix + ".elasticsearch.count.esRefresh"),
    CounterElasticSearchRefreshAlreadyRunning(AppPrefix + ".elasticsearch.count.esRefreshAlreadyRunning"),
    CounterElasticSearchUnmarkedDatabaseThreshholdReached(
            AppPrefix + ".elasticsearch.count.unmarkedDatabasesThresholdReached"),
    CounterElasticSearchUnmarkedTableThreshholdReached(
            AppPrefix + ".elasticsearch.count.unmarkedTablesThresholdReached"),

    /**
     * deleteMetadata.
     */
    CounterDeleteMetaData(AppPrefix + ".count.deleteMetadata"),

    /**
     * Tracers.
     */
    TracerCreateCatalog(AppPrefix + ".traceCreateCatalog"),
    TracerCreateTable(AppPrefix + ".traceCreateTable"),
    TracerCreateDatabase(AppPrefix + ".traceCreateDatabase"),
    TracerCreateMView(AppPrefix + ".traceCreateMView"),
    TracerDeleteDatabase(AppPrefix + ".traceDeleteDatabase"),
    TracerDeleteTablePartitions(AppPrefix + ".traceDeleteTablePartitions"),
    TracerDeleteMViewPartitions(AppPrefix + ".traceDeleteMViewPartitions"),
    TracerDeleteTable(AppPrefix + ".traceDropTable"),
    TracerDeleteMView(AppPrefix + ".traceDeleteMView"),
    TracerGetCatalog(AppPrefix + ".traceGetMetadata"),
    TracerGetCatalogNames(AppPrefix + ".traceGetCatalogNames"),
    TracerGetDatabase(AppPrefix + ".traceGetDatabase"),
    TracerGetMViewPartitions(AppPrefix + ".traceGetMViewPartitions"),
    TracerGetTablePartitions(AppPrefix + ".traceGetTablePartitions"),
    TracerGetTable(AppPrefix + ".traceGetTable"),
    TracerGetMView(AppPrefix + ".traceGetMView"),
    TracerGetCatalogMViews(AppPrefix + ".traceGetCatalogMViews"),
    TracerGetTableMViews(AppPrefix + ".traceGetTableMViews"),
    TracerRenameTable(AppPrefix + ".traceRenameTable"),
    TracerUpdateCatalog(AppPrefix + ".traceUpdateCatalog"),
    TracerUpdateTable(AppPrefix + ".traceUpdateTable"),
    TracerSaveTablePartitions(AppPrefix + ".traceSaveTablePartitions"),
    TracerSaveMViewPartitions(AppPrefix + ".traceSaveMViewPartitions"),

    /**
     * Gauges.
     */
    GaugeAddPartitions(AppPrefix + ".gaugeAddPartitions"),
    GaugeDeletePartitions(AppPrefix + ".gaugeDeletePartitions"),
    GaugeGetPartitionsCount(AppPrefix + ".gaugeGetPartitions"),

    GaugeConnectionsTotal(AppPrefix + ".connections.gauge.total"),
    GaugeConnectionsActive(AppPrefix + ".connections.gauge.active"),
    GaugeConnectionsIdle(AppPrefix + ".connections.gauge.idle"),

    /**
     * Timers.
     */
    TimerRequest(AppPrefix + ".requests.timer"),
    TimerThriftRequest(AppPrefix + ".thriftRequests.timer"),
    TimerHiveGetPartitions(AppPrefix + ".hive.getPartitions.timer"),
    TimerElasticSearchRefresh(AppPrefix + ".elasticsearch.timer.esRefresh"),

    /**
     * Status.
     */
    Status("status"), StatusSuccess("success"), StatusFailure("failure");

    private final String constant;

    LogConstants(final String constant) {
        this.constant = constant;
    }

    @Override
    public String toString() {
        return constant;
    }

    public static Map<String, String> getStatusSuccessMap() {
        return ImmutableMap.of(LogConstants.Status.name(), LogConstants.StatusSuccess.name());
    }

    public static Map<String, String> getStatusFailureMap() {
        return ImmutableMap.of(LogConstants.Status.name(), LogConstants.StatusFailure.name());
    }
}
