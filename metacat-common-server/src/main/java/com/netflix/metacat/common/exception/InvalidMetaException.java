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

import static com.netflix.metacat.common.exception.StandardErrorCode.*;

/**
 * Created by amajumdar on 5/11/15.
 */
public class InvalidMetaException extends MetacatServiceException {
    private QualifiedName tableName;
    private String partitionId;

    public InvalidMetaException(QualifiedName tableName, Throwable cause) {
        super(USER_ERROR
                , String.format("Invalid metadata for %s.", tableName)
                , cause);
        this.tableName = tableName;
    }

    public InvalidMetaException(QualifiedName tableName, String partitionId, Throwable cause) {
        super(USER_ERROR
                , String.format("Invalid metadata for %s for partition %s.", tableName, partitionId)
                , cause);
        this.tableName = tableName;
        this.partitionId = partitionId;
    }

    public InvalidMetaException(String message, Throwable cause) {
        super(USER_ERROR
                , message
                , cause);
    }
}
