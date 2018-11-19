/*
 *       Copyright 2017 Netflix, Inc.
 *          Licensed under the Apache License, Version 2.0 (the "License");
 *          you may not use this file except in compliance with the License.
 *          You may obtain a copy of the License at
 *              http://www.apache.org/licenses/LICENSE-2.0
 *          Unless required by applicable law or agreed to in writing, software
 *          distributed under the License is distributed on an "AS IS" BASIS,
 *          WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *          See the License for the specific language governing permissions and
 *          limitations under the License.
 */

package com.netflix.metacat.metadata.mysql;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.netflix.metacat.common.server.usermetadata.UserMetadataService;
import com.netflix.metacat.common.server.util.DataSourceManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Nullable;
import java.io.Reader;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Set;

/**
 * MySqlServiceUtil.
 *
 * @author zhenl
 * @since 1.1.0
 */
public final class MySqlServiceUtil {
    private MySqlServiceUtil() {
    }

    /**
     * Returns the list of string having the input ids.
     *
     * @param jdbcTemplate jdbc template
     * @param sql          query sql
     * @param item         identifier
     * @return list of results
     */
    public static Set<String> getValues(final JdbcTemplate jdbcTemplate,
                                        final String sql,
                                        final Object item) {
        try {
            return jdbcTemplate.query(sql, rs -> {
                final Set<String> result = Sets.newHashSet();
                while (rs.next()) {
                    result.add(rs.getString("value"));
                }
                return result;
            }, item);
        } catch (EmptyResultDataAccessException e) {
            return Sets.newHashSet();
        }
    }

    /**
     * load mysql data source.
     *
     * @param dataSourceManager data source manager to use
     * @param configLocation    usermetadata config location
     * @throws Exception exception to throw
     */
    public static void loadMySqlDataSource(final DataSourceManager dataSourceManager,
                                           final String configLocation) throws Exception {

        final URL url = Thread.currentThread().getContextClassLoader().getResource(configLocation);
        final Path filePath;
        if (url != null) {
            filePath = Paths.get(url.toURI());
        } else {
            filePath = FileSystems.getDefault().getPath(configLocation);
        }
        Preconditions
            .checkState(filePath != null, "Unable to read from user metadata config file '%s'", configLocation);
        final Properties connectionProperties = new Properties();
        try (Reader reader = Files.newBufferedReader(filePath, Charsets.UTF_8)) {
            connectionProperties.load(reader);
        }
        dataSourceManager.load(UserMetadataService.NAME_DATASOURCE, connectionProperties);
    }

    /**
     * Change the qualified name query parameter to wildcard query string to allow source/database/table
     * like queries. It uses '%' to represent the other field if not provided. e.g.
     * query database like string is '%/database/%'
     * query catalog and database like string is 'catalog/database/%'
     *
     * @param sourceName source name
     * @param databaseName database name
     * @param tableName table name
     * @return query string
     */
    static String qualifiedNameToWildCardQueryString(
        @Nullable final String sourceName,
        @Nullable final String databaseName,
        @Nullable final String tableName
    ) {
        if (sourceName == null && databaseName == null && tableName == null) {
            return null;
        }
        final StringBuilder builder = new StringBuilder();
        if (!StringUtils.isEmpty(sourceName)) {
            builder.append(sourceName);
        } else {
            builder.append('%');
        }
        if (StringUtils.isEmpty(databaseName) && StringUtils.isEmpty(tableName)) {
            return builder.append('%').toString(); //query source level
        }
        if (!StringUtils.isEmpty(databaseName)) {
            builder.append('/').append(databaseName);
        } else {
            builder.append("/%");
        }
        if (StringUtils.isEmpty(tableName)) {
            return builder.append('%').toString(); //database level query
        } else {
            builder.append('/').append(tableName);
        }
        builder.append('%');
        return builder.toString();
    }
}



