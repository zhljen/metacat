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

/**
 * Druid Config Constants.
 * @author zhenl
 * @since 1.1.0
 */
public final class DruidConfigConstants {
    /**
     * DRUID_SERVER.
     */
    public static final String DRUID_SERVER = "druid.server";


    /**
     * DRUID_COORDINATOR_ENDPOINT.
     */
    public static final String DRUID_COORDINATOR_ENDPOINT = "/druid/coordinator/v1/metadata/datasources";

    /**
     * DRUID_PORT.
     */
    public static final String DRUID_PORT = "druid.port";

    /**
     * DRUID_DB.
     */
    public static final String DRUID_DB = "database";


    /**
     * druid dimensions.
     */
    public static final String DIMENSIONS = "dimensions";

    /**
     * druid metrics.
     */
    public static final String METRICS = "metrics";

    /**
     * druid segments.
     */
    public static final String SEGMENTS = "segments";

    private DruidConfigConstants() {
    }
}
