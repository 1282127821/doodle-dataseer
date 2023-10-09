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

import org.doodle.broker.client.BrokerClientRSocketRequester;
import org.doodle.dataseer.report.server.*;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@AutoConfiguration
@ConditionalOnClass(DataSeerReportServerProperties.class)
@EnableConfigurationProperties(DataSeerReportServerProperties.class)
@EnableMongoAuditing
@EnableMongoRepositories(basePackageClasses = DataSeerReportServerLogRepo.class)
public class DataSeerReportServerAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public DataSeerReportServerMapper dataSeerReportServerMapper() {
    return new DataSeerReportServerMapper();
  }

  @Bean
  @ConditionalOnMissingBean
  public DataSeerReportServerLogService dataSeerReportServerLogService(
      DataSeerReportServerMapper mapper, DataSeerReportServerLogRepo logRepo) {
    return new DataSeerReportServerLogService(mapper, logRepo);
  }

  @Configuration(proxyBeanMethods = false)
  @ConditionalOnClass(GroupedOpenApi.class)
  @ConditionalOnWebApplication
  public static class SpringDocConfiguration {
    @Bean
    public GroupedOpenApi reportGroupedOpenApi() {
      return GroupedOpenApi.builder()
          .group("dataseer-report-apis")
          .packagesToScan(DataSeerReportServerProperties.class.getPackage().getName())
          .build();
    }
  }

  @AutoConfiguration
  @ConditionalOnClass(BrokerClientRSocketRequester.class)
  @ConditionalOnBean(BrokerClientRSocketRequester.class)
  public static class RSocketConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public DataSeerReportServerRSocketController dataSeerReportServerRSocketController(
        DataSeerReportServerMapper mapper, DataSeerReportServerLogService logService) {
      return new DataSeerReportServerRSocketController(mapper, logService);
    }
  }

  @AutoConfiguration
  @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
  public static class ServletConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public DataSeerReportServerServletController dataSeerReportServerServletController(
        DataSeerReportServerLogService logService) {
      return new DataSeerReportServerServletController(logService);
    }
  }
}
