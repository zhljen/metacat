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

import java.util.Collections;
import java.util.List;

/**
 * Created by amajumdar on 6/17/16.
 */
public class BaseType implements Type {
    public static final Type BOOLEAN = new BaseType(Base.BOOLEAN);
    public static final Type TINYINT = new BaseType(Base.TINYINT);
    public static final Type SMALLINT = new BaseType(Base.SMALLINT);
    public static final Type INT = new BaseType(Base.INT);
    public static final Type BIGINT = new BaseType(Base.BIGINT);
    public static final Type FLOAT = new BaseType(Base.FLOAT);
    public static final Type DOUBLE = new BaseType(Base.DOUBLE);
    public static final Type STRING = new BaseType(Base.STRING);
    public static final Type JSON = new BaseType(Base.JSON);
    public static final Type VARBINARY = new BaseType(Base.VARBINARY);
    public static final Type DATE = new BaseType(Base.DATE);
    public static final Type TIME = new BaseType(Base.TIME);
    public static final Type TIME_WITH_TIME_ZONE = new BaseType(Base.TIME_WITH_TIME_ZONE);
    public static final Type TIMESTAMP = new BaseType(Base.TIMESTAMP);
    public static final Type TIMESTAMP_WITH_TIME_ZONE = new BaseType(Base.TIMESTAMP_WITH_TIME_ZONE);
    public static final Type INTERVAL_YEAR_TO_MONTH = new BaseType(Base.INTERVAL_YEAR_TO_MONTH);
    public static final Type INTERVAL_DAY_TO_SECOND = new BaseType(Base.INTERVAL_DAY_TO_SECOND);
    private Base base;
    private String sourceType;
    private List<Object> parameters;

    public BaseType(Base base) {
        this( base, null, null);
    }

    public BaseType(Base base, List<Object> parameters, String sourceType) {
        this.base = base;
        this.parameters = parameters==null? Collections.emptyList(): parameters;
        this.sourceType = sourceType;
    }

    public BaseType(Base base, String sourceType) {
        this( base, null, sourceType);
    }

    @Override
    public Base getBaseType() {
        return base;
    }

    @Override
    public String getSignature() {
        StringBuilder result = new StringBuilder( base.getType());
        if (!parameters.isEmpty()) {
            result.append("(");
            boolean first = true;
            for (Object parameter : parameters) {
                if (!first) {
                    result.append(",");
                } else {
                    first = false;
                }
                if (parameter instanceof String) {
                    result.append("'").append(parameter).append("'");
                }
                else {
                    result.append(parameter.toString());
                }
            }
            result.append(")");
        }
        return result.toString();
    }

    @Override
    public String getSourceType() {
        return sourceType;
    }
}
