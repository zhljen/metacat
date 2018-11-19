/*
 *   Copyright 2018 Netflix, Inc.
 *
 *      Licensed under the Apache License, Version 2.0 (the "License");
 *      you may not use this file except in compliance with the License.
 *      You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *      Unless required by applicable law or agreed to in writing, software
 *      distributed under the License is distributed on an "AS IS" BASIS,
 *      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *      See the License for the specific language governing permissions and
 *      limitations under the License.
 *
 */

package com.netflix.metacat.metadata.mysql

import spock.lang.Specification

class MySqlServiceUtilSpec extends Specification {
    def testQualifedNameToWildcardQueryString() {
        when:
        def result = MySqlServiceUtil.qualifiedNameToWildCardQueryString(sourceName, databaseName, tableName)
        then:
        result == ret
        where:
        sourceName  | databaseName | tableName | ret
        null        | null         | null      | null
        "prodhive"  | "database_1" | "abcd"    | "prodhive/database_1/abcd%"
        "prodhive"  | ""           | ""        | "prodhive%"
        "prodhive"  | null         | null      | "prodhive%"
        "prodhive"  | "database_1" | ""        | "prodhive/database_1%"
        "prodhive"  | "database_1" | null      | "prodhive/database_1%"
        ""          | "database_1" | ""        | "%/database_1%"
        null        | "database_1" | ""        | "%/database_1%"
        null        | "database_1" | null      | "%/database_1%"
        ""          | "database_1" | "abcd"    | "%/database_1/abcd%"
        ""          | ""           | "abcd"    | "%/%/abcd%"
    }
}
