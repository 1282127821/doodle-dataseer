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

import org.doodle.dataseer.server.DataSeerServerLogRSocketController;
import org.doodle.dataseer.server.DataSeerServerLogServletController;
import org.doodle.dataseer.server.DataSeerServerProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@ConditionalOnClass(DataSeerServerProperties.class)
@EnableConfigurationProperties(DataSeerServerProperties.class)
public class DataSeerServerAutoConfiguration {

  @AutoConfiguration
  public static class RSocketConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public DataSeerServerLogRSocketController dataSeerServerLogRSocketController() {
      return new DataSeerServerLogRSocketController();
    }
  }

  @AutoConfiguration
  @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
  public static class ServletConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public DataSeerServerLogServletController dataSeerServerLogServletController() {
      return new DataSeerServerLogServletController();
    }
  }
}
