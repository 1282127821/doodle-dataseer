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
import org.doodle.design.common.Result;
import org.doodle.design.dataseer.DataSeerReportLogPageOps;
import org.doodle.design.dataseer.model.payload.reply.DataSeerReportLogPageReply;
import org.doodle.design.dataseer.model.payload.request.DataSeerReportLogPageRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class DataSeerReportServerServletController implements DataSeerReportLogPageOps.Servlet {

  @PostMapping(DataSeerReportLogPageOps.Servlet.PAGE_MAPPING)
  @Override
  public Result<DataSeerReportLogPageReply> page(DataSeerReportLogPageRequest request) {
    return Result.bad();
  }
}
