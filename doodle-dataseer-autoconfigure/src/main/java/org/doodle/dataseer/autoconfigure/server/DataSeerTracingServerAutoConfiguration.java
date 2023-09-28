/*
 * Copyright (c) 2022-present Doodle. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.doodle.dataseer.autoconfigure.server;

import org.doodle.broker.autoconfigure.client.BrokerClientAutoConfiguration;
import org.doodle.broker.client.BrokerClientRSocketRequester;
import org.doodle.dataseer.tracing.server.DataSeerTracingServerMapper;
import org.doodle.dataseer.tracing.server.DataSeerTracingServerProperties;
import org.doodle.dataseer.tracing.server.DataSeerTracingServerRSocketController;
import org.doodle.dataseer.tracing.server.DataSeerTracingServerServletController;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration(after = BrokerClientAutoConfiguration.class)
@ConditionalOnClass(DataSeerTracingServerProperties.class)
@EnableConfigurationProperties(DataSeerTracingServerProperties.class)
public class DataSeerTracingServerAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public DataSeerTracingServerMapper dataSeerTracingServerMapper() {
    return new DataSeerTracingServerMapper();
  }

  @AutoConfiguration
  @ConditionalOnClass(BrokerClientRSocketRequester.class)
  @ConditionalOnBean(BrokerClientRSocketRequester.class)
  public static class RSocketConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public DataSeerTracingServerRSocketController dataSeerTracingServerRSocketController() {
      return new DataSeerTracingServerRSocketController();
    }
  }

  @AutoConfiguration
  @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
  public static class ServletConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public DataSeerTracingServerServletController dataSeerTracingServerServletController() {
      return new DataSeerTracingServerServletController();
    }
  }
}
