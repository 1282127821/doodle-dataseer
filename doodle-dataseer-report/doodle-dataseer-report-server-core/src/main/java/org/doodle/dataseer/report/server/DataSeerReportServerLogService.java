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
package org.doodle.dataseer.report.server;

import java.util.Collections;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.doodle.design.common.model.PageRequest;
import org.doodle.design.dataseer.model.info.ReportLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class DataSeerReportServerLogService {
  DataSeerReportServerMapper mapper;
  DataSeerReportServerLogRepo logRepo;

  public Mono<Void> reportMono(ReportLog log) {
    return Mono.fromRunnable(() -> report(log));
  }

  public void report(ReportLog log) {
    logRepo.save(mapper.toEntity(log));
  }

  public Mono<List<ReportLog>> pageMono(PageRequest request) {
    return Mono.fromCallable(() -> page(request));
  }

  public List<ReportLog> page(PageRequest request) {
    Page<DataSeerReportServerLogEntity> page =
        logRepo.findAll(Pageable.ofSize(request.getPageSize()).withPage(request.getPageNumber()));
    List<DataSeerReportServerLogEntity> content = page.getContent();
    return !CollectionUtils.isEmpty(content)
        ? content.stream().map(mapper::toPojo).toList()
        : Collections.emptyList();
  }
}
