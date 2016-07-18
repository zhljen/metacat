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

package com.netflix.metacat.common.exception;

import com.netflix.metacat.common.QualifiedName;

import static java.lang.String.format;

/**
 * Created by amajumdar on 7/8/16.
 */
public class TableNotFoundException
        extends NotFoundException
{
    private final QualifiedName tableName;

    public TableNotFoundException(QualifiedName tableName)
    {
        this(tableName, format("Table '%s' not found", tableName));
    }

    public TableNotFoundException(QualifiedName tableName, String message)
    {
        super(message);
        if (tableName == null) {
            throw new NullPointerException("tableName is null");
        }
        this.tableName = tableName;
    }

    public TableNotFoundException(QualifiedName tableName, Throwable cause)
    {
        this(tableName, format("Table '%s' not found", tableName), cause);
    }

    public TableNotFoundException(QualifiedName tableName, String message, Throwable cause)
    {
        super(message, cause);
        if (tableName == null) {
            throw new NullPointerException("tableName is null");
        }
        this.tableName = tableName;
    }

    public QualifiedName getTableName()
    {
        return tableName;
    }
}
