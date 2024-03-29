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

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.doodle.design.dataseer.*;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class DataSeerReportServerRSocketController
    implements DataSeerReportLogUploadOps.RSocket, DataSeerReportLogPageOps.RSocket {
  DataSeerReportServerMapper mapper;
  DataSeerReportServerLogService logService;

  @MessageMapping(DataSeerReportLogUploadOps.RSocket.UPLOAD_MAPPING)
  @Override
  public Mono<Void> report(DataSeerReportLogUploadRequest request) {
    return Mono.fromSupplier(request::getReportLog)
        .map(mapper::fromProto)
        .flatMap(logService::reportMono);
  }

  @MessageMapping(DataSeerReportLogPageOps.RSocket.PAGE_MAPPING)
  @Override
  public Mono<DataSeerReportLogPageReply> page(DataSeerReportLogPageRequest request) {
    return Mono.fromSupplier(request::getPage)
        .map(mapper::fromProto)
        .flatMap(logService::pageMono)
        .map(mapper::toReportLogList)
        .map(mapper::toReportLogPageReply);
  }

  @MessageExceptionHandler(DataSeerReportServerExceptions.Page.class)
  Mono<DataSeerReportLogPageReply> onPageException(DataSeerReportServerExceptions.Page ignored) {
    return Mono.empty();
  }
}
