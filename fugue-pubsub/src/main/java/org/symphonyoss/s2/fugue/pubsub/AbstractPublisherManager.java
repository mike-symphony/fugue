/*
 *
 *
 * Copyright 2018 Symphony Communication Services, LLC.
 *
 * Licensed to The Symphony Software Foundation (SSF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.symphonyoss.s2.fugue.pubsub;

import org.symphonyoss.s2.fugue.FugueLifecycleComponent;
import org.symphonyoss.s2.fugue.naming.INameFactory;
import org.symphonyoss.s2.fugue.naming.TopicName;

public abstract class AbstractPublisherManager<P,T extends AbstractPublisherManager<P,T>>
  extends FugueLifecycleComponent<T>
  implements IPublisherManager<P>
{
  private static final String TRACE_TOPIC_NAME = "trace";
  
  protected final INameFactory nameFactory_;

  protected AbstractPublisherManager(INameFactory nameFactory, Class<T> type)
  {
    super(type);
    nameFactory_ = nameFactory;
  }
  
  @Override
  public IPublisher<P> getTracePublisher()
  {
    return getPublisherByName(TRACE_TOPIC_NAME);
  }
  
  @Override
  public IPublisher<P> getPublisherByName(String topicId)
  {
    return getPublisherByName(nameFactory_.getTopicName(topicId));
  }

  @Override
  public IPublisher<P> getPublisherByName(String serviceId, String topicId)
  {
    return getPublisherByName(nameFactory_.getTopicName(serviceId, topicId));
  }

  protected abstract IPublisher<P> getPublisherByName(TopicName topicName);
}
