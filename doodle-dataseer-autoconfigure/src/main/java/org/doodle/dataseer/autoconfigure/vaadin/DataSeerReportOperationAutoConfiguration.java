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
package org.doodle.dataseer.autoconfigure.vaadin;

import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.sidenav.SideNavItem;
import org.doodle.boot.vaadin.EnableVaadin;
import org.doodle.dataseer.operation.vaadin.DataSeerOperationVaadinProperties;
import org.doodle.dataseer.operation.vaadin.views.DataSeerOperationVaadinView;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration(before = DataSeerVaadinAutoConfiguration.class)
@ConditionalOnClass(DataSeerOperationVaadinProperties.class)
@EnableConfigurationProperties(DataSeerOperationVaadinProperties.class)
@EnableVaadin(DataSeerOperationVaadinProperties.PREFIX_VIEWS)
public class DataSeerReportOperationAutoConfiguration {

  @Bean
  public DataSeerVaadinAutoConfiguration.DataSeerSideNavItemSupplier
      dataSeerOperationSideNavView() {
    return (authenticationContext) ->
        new SideNavItem("操作", DataSeerOperationVaadinView.class, VaadinIcon.UPLOAD_ALT.create());
  }
}
