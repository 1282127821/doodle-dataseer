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
import com.vaadin.flow.spring.security.AuthenticationContext;
import java.util.function.Function;
import org.doodle.boot.vaadin.views.VaadinSideNavItemSupplier;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class DataSeerVaadinAutoConfiguration {

  @AutoConfiguration
  @ConditionalOnBean(DataSeerSideNavItemSupplier.class)
  public static class LogViewConfiguration {
    @Bean
    public VaadinSideNavItemSupplier dataSeerLogSideNavView(
        ObjectProvider<DataSeerSideNavItemSupplier> provider) {
      return (authenticationContext) -> {
        SideNavItem item = new SideNavItem("日志组件");
        item.setPrefixComponent(VaadinIcon.UPLOAD.create());
        provider
            .orderedStream()
            .map(supplier -> supplier.apply(authenticationContext))
            .forEach(item::addItem);
        return item;
      };
    }
  }

  @FunctionalInterface
  public interface DataSeerSideNavItemSupplier
      extends Function<AuthenticationContext, SideNavItem> {}
}
