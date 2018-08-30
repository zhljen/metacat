/*
 *  Copyright 2018 Netflix, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.netflix.metacat.common.server.usermetadata;

import com.netflix.metacat.common.QualifiedName;
import com.netflix.metacat.common.exception.MetacatException;
import com.netflix.metacat.common.server.properties.Config;
import com.netflix.servo.util.VisibleForTesting;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Config based authorization Service implementation.
 *
 * @author zhenl
 * @since 1.2.0
 */
public class DefaultAuthorizationService implements AuthorizationService {
    //catalog+database name as the acl control key, and userNames as the value
    private final Map<String, Set<String>> createACL;
    private final Map<String, Set<String>> deleteACL;

    /**
     * Constructor.
     *
     * @param config metacat config
     */
    public DefaultAuthorizationService(
        final Config config
    ) {
        this.createACL = this.getACLMap(config.getMetacatCreateAcl());
        this.deleteACL = this.getACLMap(config.getMetacatDeleteAcl());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isUnauthorized(final String userName,
                                  final QualifiedName name,
                                  final MetacatACL op) {
        switch (op) {
            case metacatCreate:
                return isUnauthorized(createACL, userName, name);
            case metacatDelete:
                return isUnauthorized(deleteACL, userName, name);
            default:
                return true;
        }
    }

    private boolean isUnauthorized(final Map<String, Set<String>> accessACL,
                                   final String userName,
                                   final QualifiedName name) {
        //for the names without database
        if (!name.isCatalogDefinition() || !name.isDatabaseDefinition()) {
            return false;
        }
        final Set<String> users =
            accessACL.get(QualifiedName.ofDatabase(name.getCatalogName(), name.getDatabaseName()).toString());
        return (users != null) && !users.isEmpty() && !users.contains(userName);
    }

    /**
     * Parse the configuration to get operation control. The control is at userName level
     * and the controlled operations include create, delete, and rename for table.
     * The format is below.
     * db1:user1,user2|db2:user1,user2
     *
     * @param aclConfig the config strings for dbs
     * @return acl config
     */
    @VisibleForTesting
    private Map<String, Set<String>> getACLMap(final String aclConfig) {
        final Map<String, Set<String>> aclMap = new HashMap<>();
        try {
            for (String aclstr: StringUtils.split(aclConfig, "|")) {
                aclMap.put(aclstr.substring(0, aclstr.indexOf(":")),
                    new HashSet<>(Arrays.asList(StringUtils.split(aclstr.substring(aclstr.indexOf(":") + 1), ","))));
            }

        } catch (Exception e) {
            throw new MetacatException("metacat acl property parsing error" + e.getMessage());
        }
        return aclMap;
    }

}
