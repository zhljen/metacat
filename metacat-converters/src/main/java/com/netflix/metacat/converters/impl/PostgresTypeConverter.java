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

import com.facebook.presto.spi.type.Type;
import com.facebook.presto.spi.type.TypeManager;
import com.netflix.metacat.converters.TypeConverter;

/**
 * Created by amajumdar on 5/4/16.
 */
public class PostgresTypeConverter implements TypeConverter {
    @Override
    public Type toType(String type, TypeManager typeManager) {
        return null;
    }

    @Override
    public String fromType(Type type) {
        return null;
    }
}
