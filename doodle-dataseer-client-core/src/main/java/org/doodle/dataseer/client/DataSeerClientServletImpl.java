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
package org.doodle.dataseer.client;

import java.util.Collections;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.doodle.design.common.Result;
import org.doodle.design.dataseer.DataSeerLogUploadOps;
import org.doodle.design.dataseer.model.payload.request.LogUploadRequest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class DataSeerClientServletImpl implements DataSeerClientServlet {
  RestTemplate restTemplate;

  static final ParameterizedTypeReference<Result<Void>> UPLOAD_RESPONSE_TYPE =
      new ParameterizedTypeReference<>() {};

  @Override
  public Result<Void> upload(LogUploadRequest request) {
    return restTemplate
        .exchange(
            DataSeerLogUploadOps.Servlet.UPLOAD_MAPPING,
            HttpMethod.POST,
            new HttpEntity<>(request, createHttpHeaders()),
            UPLOAD_RESPONSE_TYPE)
        .getBody();
  }

  HttpHeaders createHttpHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    return HttpHeaders.readOnlyHttpHeaders(headers);
  }
}
