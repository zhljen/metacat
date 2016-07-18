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

package com.netflix.metacat.converters.impl;

import com.facebook.presto.spi.PrestoException;
import com.facebook.presto.spi.type.DateType;
import com.facebook.presto.spi.type.StandardTypes;
import com.facebook.presto.spi.type.Type;
import com.facebook.presto.spi.type.TypeManager;
import com.facebook.presto.type.CharType;
import com.facebook.presto.type.DecimalType;
import com.facebook.presto.type.FloatType;
import com.facebook.presto.type.IntType;
import com.facebook.presto.type.SmallIntType;
import com.facebook.presto.type.TinyIntType;
import com.facebook.presto.type.VarcharType;
import com.google.common.collect.ImmutableMap;
import com.netflix.metacat.converters.TypeConverter;

import java.sql.JDBCType;
import java.sql.Types;
import java.util.Map;

import static com.facebook.presto.spi.StandardErrorCode.NOT_SUPPORTED;
import static com.facebook.presto.spi.type.BigintType.BIGINT;
import static com.facebook.presto.spi.type.BooleanType.BOOLEAN;
import static com.facebook.presto.spi.type.DateType.DATE;
import static com.facebook.presto.spi.type.DoubleType.DOUBLE;
import static com.facebook.presto.spi.type.TimeType.TIME;
import static com.facebook.presto.spi.type.TimeWithTimeZoneType.TIME_WITH_TIME_ZONE;
import static com.facebook.presto.spi.type.TimestampType.TIMESTAMP;
import static com.facebook.presto.spi.type.TimestampWithTimeZoneType.TIMESTAMP_WITH_TIME_ZONE;
import static com.facebook.presto.spi.type.VarbinaryType.VARBINARY;
import static com.facebook.presto.spi.type.VarcharType.VARCHAR;
import static com.facebook.presto.type.FloatType.FLOAT;
import static com.facebook.presto.type.IntType.INT;
import static com.facebook.presto.type.SmallIntType.SMALL_INT;
import static com.facebook.presto.type.StringType.STRING;
import static com.facebook.presto.type.TinyIntType.TINY_INT;


/**
 * Created by amajumdar on 5/4/16.
 */
public class MySqlTypeConverter implements TypeConverter {
    private static final Map<Type, String> SQL_TYPES = ImmutableMap.<Type, String>builder()
            .put(BOOLEAN, JDBCType.BOOLEAN.getName().toLowerCase())
            .put(BIGINT, JDBCType.BIGINT.getName().toLowerCase())
            .put(INT, IntType.TYPE)
            .put(TINY_INT, JDBCType.TINYINT.getName().toLowerCase())
            .put(SMALL_INT, JDBCType.SMALLINT.getName().toLowerCase())
            .put(FLOAT, JDBCType.FLOAT.getName().toLowerCase())
            .put(DOUBLE, "double precision")
            .put(VARCHAR, "mediumtext")
            .put(VARBINARY, "mediumblob")
            .put(DATE, JDBCType.DATE.getName().toLowerCase())
            .put(TIME, JDBCType.TIME.getName().toLowerCase())
            .put(TIME_WITH_TIME_ZONE, JDBCType.TIME.getName().toLowerCase())
            .put(TIMESTAMP, "datetime")
            .put(TIMESTAMP_WITH_TIME_ZONE, "datetime")
            .build();

    @Override
    public Type toType(String type, TypeManager typeManager) {
        String typeUpperCase = type.toUpperCase();
        if (StandardTypes.BOOLEAN.equalsIgnoreCase(type) || JDBCType.BIT.getName().equals(typeUpperCase)) {
            return BOOLEAN;
        } else if (JDBCType.TINYINT.getName().equals(typeUpperCase)) {
            return TINY_INT;
        } else if (JDBCType.SMALLINT.getName().equals(typeUpperCase)) {
            return SMALL_INT;
        } else if (IntType.TYPE.equalsIgnoreCase(type)) {
            return INT;
        } else if (JDBCType.BIGINT.getName().equals(typeUpperCase)) {
            return BIGINT;
        } else if (JDBCType.FLOAT.getName().equals(typeUpperCase) || JDBCType.REAL.getName().equals(typeUpperCase)) {
            return FLOAT;
        } else if (JDBCType.DOUBLE.getName().equals(typeUpperCase) || JDBCType.NUMERIC.getName().equals(typeUpperCase)) {
            return DOUBLE;
        } else if (typeUpperCase.startsWith(JDBCType.NCHAR.getName())
                || typeUpperCase.startsWith(JDBCType.NVARCHAR.getName())
                || typeUpperCase.startsWith(JDBCType.LONGVARCHAR.getName())
                || typeUpperCase.startsWith(JDBCType.LONGNVARCHAR.getName())) {
            return STRING;
        } else {
            throw new PrestoException(NOT_SUPPORTED, "Unsupported type: " + type);
        }
    }

    @Override
    public String fromType(Type type) {
        String sqlType = SQL_TYPES.get(type);
        if (sqlType != null) {
            return sqlType;
        }
        throw new PrestoException(NOT_SUPPORTED, "Unsupported type: " + type.getTypeSignature());
    }

    public Type toPrestoType(int jdbcType)
    {
        switch (jdbcType) {
        case Types.BIT:
        case Types.BOOLEAN:
            return BOOLEAN;
        case Types.TINYINT:
            return TINY_INT;
        case Types.SMALLINT:
            return SMALL_INT;
        case Types.INTEGER:
            return INT;
        case Types.BIGINT:
            return BIGINT;
        case Types.FLOAT:
        case Types.REAL:
            return FLOAT;
        case Types.DOUBLE:
        case Types.NUMERIC:
        case Types.DECIMAL:
            return DOUBLE;
        case Types.CHAR:
        case Types.NCHAR:
        case Types.VARCHAR:
        case Types.NVARCHAR:
        case Types.LONGVARCHAR:
        case Types.LONGNVARCHAR:
            return STRING;
        case Types.BINARY:
        case Types.VARBINARY:
        case Types.LONGVARBINARY:
            return VARBINARY;
        case Types.DATE:
            return DATE;
        case Types.TIME:
            return TIME;
        case Types.TIMESTAMP:
            return TIMESTAMP;
        }
        return null;
    }
}
