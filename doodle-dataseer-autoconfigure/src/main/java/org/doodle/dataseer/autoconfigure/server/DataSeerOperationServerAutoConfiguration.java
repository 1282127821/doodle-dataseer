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
import org.doodle.dataseer.operation.server.DataSeerOperationServerMapper;
import org.doodle.dataseer.operation.server.DataSeerOperationServerProperties;
import org.doodle.dataseer.operation.server.DataSeerOperationServerRSocketController;
import org.doodle.dataseer.operation.server.DataSeerOperationServerServletController;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration(after = BrokerClientAutoConfiguration.class)
@ConditionalOnClass(DataSeerOperationServerProperties.class)
@EnableConfigurationProperties(DataSeerOperationServerProperties.class)
public class DataSeerOperationServerAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public DataSeerOperationServerMapper dataSeerOperationServerMapper() {
    return new DataSeerOperationServerMapper();
  }

  @AutoConfiguration
  @ConditionalOnClass(BrokerClientRSocketRequester.class)
  @ConditionalOnBean(BrokerClientRSocketRequester.class)
  public static class RSocketConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public DataSeerOperationServerRSocketController dataSeerOperationServerRSocketController() {
      return new DataSeerOperationServerRSocketController();
    }
  }

  @AutoConfiguration
  @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
  public static class ServletConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public DataSeerOperationServerServletController dataSeerOperationServerServletController() {
      return new DataSeerOperationServerServletController();
    }
  }
}
