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

package com.netflix.metacat.connector.druid

import com.netflix.metacat.common.QualifiedName
import com.netflix.metacat.common.dto.Pageable
import com.netflix.metacat.common.dto.Sort
import com.netflix.metacat.common.dto.SortOrder
import com.netflix.metacat.common.server.connectors.ConnectorRequestContext
import com.netflix.metacat.common.server.connectors.exception.ConnectorException
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class DruidConnectorTableServiceSpec extends Specification{
    @Shared
    IMetacatDruidClient metacatHiveClient = Mock(IMetacatDruidClient)
    @Shared
    ConnectorRequestContext connectorContext = new ConnectorRequestContext(1, null)
    @Shared
    tableNames = [
        QualifiedName.ofTable("druid", "database", "devtable2"),
        QualifiedName.ofTable("druid", "database", "devtable3"),
        QualifiedName.ofTable("druid", "database", "testtable1"),
        QualifiedName.ofTable("druid", "database", "testtable2"),
    ]
    @Shared
    druidTableNames = [
        "testtable1",
        "testtable2",
        "devtable2",
        "devtable3"
    ]

    @Unroll
    def "Test for listNames"() {
        def client = Mock(IMetacatDruidClient)
        def druidTableService = new DruidConnectorTableService(client)

        when:
        def tbls = druidTableService.listNames(connectorContext,
            QualifiedName.ofDatabase("druid", "database"),
            null,
            order,
            null)

        then:
        1 * client.getAllDataSources() >> druidTableNames
        result == tbls

        where:
        order                                 | result
        new Sort(null, SortOrder.ASC)  | tableNames
        new Sort(null, SortOrder.DESC) | tableNames.reverse()
    }

    @Unroll
    def "Test for listNames tables with page"() {
        def client = Mock(IMetacatDruidClient)
        def druidTableService = new DruidConnectorTableService(client)

        when:
        def tbls = druidTableService.listNames(
            connectorContext, QualifiedName.ofDatabase("druid", "database"), prefix, null, pageable)

        then:
        1 * client.getAllDataSources() >> druidTableNames
        tbls == result

        where:
        prefix                                             | pageable           | result
        null                                               | new Pageable(2, 1) | [QualifiedName.ofTable("druid", "database", "testtable2"), QualifiedName.ofTable("druid", "database", "devtable2")]
        QualifiedName.ofDatabase("druid", "database")      | new Pageable(2, 1) | [QualifiedName.ofTable("druid", "database", "testtable2"), QualifiedName.ofTable("druid", "database", "devtable2")]
        QualifiedName.ofTable("druid", "database", "test") | new Pageable(2, 1) | [QualifiedName.ofTable("druid", "database", "testtable2")]
        QualifiedName.ofTable("druid", "database", "test") | new Pageable(1, 0) | [QualifiedName.ofTable("druid", "database", "testtable1")]
        QualifiedName.ofTable("druid", "database", "test") | new Pageable(0, 0) | []
    }

    def "Test for listNames tables exceptions"() {
        def client = Mock(IMetacatDruidClient)
        def druidTableService = new DruidConnectorTableService(client)

        when:
        druidTableService.listNames(
            connectorContext, QualifiedName.ofDatabase("druid", "database"), null, null, null)

        then:
        1 * client.getAllDataSources() >> { throw new Exception() }
        thrown ConnectorException
    }

    def "Test for get table"() {
        def name = QualifiedName.ofTable("druid", "database", "testtable1")
        def client = Mock(IMetacatDruidClient)
        def druidTableService = new DruidConnectorTableService(client)
        def datareturn = ['dimensions': 'height, age', 'metrics': 'max']

        when:
        def tbls = druidTableService.get(connectorContext, name)

        then:
        1 * client.getDataSourceByName(_ as String) >> datareturn
        tbls.fields.size() == 2
        tbls.fields.get(0).name == "dimensions"
        tbls.fields.get(0).defaultValue == "height, age"
        tbls.fields.get(1).name == "metrics"
        tbls.fields.get(1).defaultValue == "max"

    }
}
