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

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.doodle.broker.client.BrokerClientRSocketRequester;
import org.doodle.design.broker.frame.BrokerFrame;
import org.doodle.design.broker.frame.BrokerFrameUtils;
import org.doodle.design.dataseer.DataSeerLogUploadOps;
import org.doodle.design.dataseer.LogUploadRequest;
import reactor.core.publisher.Mono;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BrokerDataSeerClientRSocket implements DataSeerClientRSocket {
  BrokerClientRSocketRequester requester;
  BrokerFrame frame;

  public BrokerDataSeerClientRSocket(
      BrokerClientRSocketRequester requester, DataSeerClientProperties properties) {
    this.requester = requester;
    this.frame = BrokerFrameUtils.unicast(properties.getServer().getTag());
  }

  @Override
  public Mono<Void> upload(LogUploadRequest request) {
    return this.requester.route(DataSeerLogUploadOps.RSocket.UPLOAD_MAPPING).data(request).send();
  }
}
