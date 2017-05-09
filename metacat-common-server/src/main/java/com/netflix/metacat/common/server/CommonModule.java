/*
 *
 *  Copyright 2016 Netflix, Inc.
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
 *
 */
package com.netflix.metacat.common.server;

/**
 * Guice module.
 */
public class CommonModule /* extends AbstractModule */ {
//    @Override
//    protected void configure() {
//        final Config config = new ArchaiusConfigImpl();
//
//        bind(Config.class).toInstance(config);
//        bind(MetacatJson.class).toInstance(MetacatJsonLocator.INSTANCE);
//        bind(DeadEventHandler.class).asEagerSingleton();
//        bind(DataSourceManager.class).toInstance(DataSourceManager.get());
//        bind(MetacatEventBus.class).asEagerSingleton();
//        bind(ConverterUtil.class).asEagerSingleton();
//        bind(DozerTypeConverter.class).asEagerSingleton();
//        binder().bind(ConnectorTypeConverter.class).toProvider(TypeConverterFactory.class);
//        bind(TypeConverterFactory.class).asEagerSingleton();
//
//        // Injecting statics is a bad pattern and should be avoided, but I am doing it as a first step to allow
//        // us to remove the hard coded username.
//        binder().requestStaticInjection(Lookup.class, TagItem.class);
//        bind(ThreadServiceManager.class).asEagerSingleton();
//    }
}
