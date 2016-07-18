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

package com.netflix.metacat.common.type;

/**
 * Created by amajumdar on 6/17/16.
 */
public enum Base {
    BOOLEAN("boolean", false, false),
    TINYINT("tinyint", false, false),
    SMALLINT("smallint", false, false),
    INT("int", false, false),
    BIGINT("bigint", false, false),
    FLOAT("float", false, false),
    DOUBLE("double", false, false),
    DECIMAL("decimal", true, false),
    CHAR("char", true, false),
    VARCHAR("varchar", true, false),
    STRING("string", false, false),
    JSON("json", false, false),
    VARBINARY("varbinary", false, false),
    DATE("date", false, false),
    TIME("time", false, false),
    TIME_WITH_TIME_ZONE("time with time zone", false, false),
    TIMESTAMP("timestamp", false, false),
    TIMESTAMP_WITH_TIME_ZONE("timestamp with time zone", false, false),
    INTERVAL_YEAR_TO_MONTH("interval year to month", false, false),
    INTERVAL_DAY_TO_SECOND("interval day to second", false, false),
    UNKNOWN("unknown", false, false),
    ARRAY("array", false, true),
    ROW("row", false, true),
    MAP("map", false, true);


    private String type;
    private boolean isParametricType;
    private boolean isComplexType;
    Base(String type, boolean isParametricType, boolean isComplexType) {
        this.type = type;
        this.isParametricType = isParametricType;
        this.isComplexType = isComplexType;
    }

    /* signature of the type */
    public String getType() {
        return type;
    }

    /* Type has literal parameters. Ex. char(10) */
    public boolean isParametricType() {
        return isParametricType;
    }

    /* Type is a complex type */
    public boolean isComplexType() {
        return isComplexType;
    }

    public Base fromName(String name){
        try{
            return Base.valueOf(name);
        } catch(Exception e){
            return UNKNOWN;
        }
    }
}
