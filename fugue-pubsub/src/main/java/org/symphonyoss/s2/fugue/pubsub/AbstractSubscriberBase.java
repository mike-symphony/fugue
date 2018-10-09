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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import org.symphonyoss.s2.common.fluent.IFluent;
import org.symphonyoss.s2.fugue.FugueLifecycleComponent;
import org.symphonyoss.s2.fugue.naming.INameFactory;
import org.symphonyoss.s2.fugue.naming.TopicName;
import org.symphonyoss.s2.fugue.pipeline.IThreadSafeRetryableConsumer;

/**
 * Base class for subscriber managers and admin controllers.
 * 
 * @author Bruce Skingle
 *
 * @param <P> Type of payload received.
 * @param <T> Type of concrete manager, needed for fluent methods.
 */
public abstract class AbstractSubscriberBase<P, T extends IFluent<T>> extends FugueLifecycleComponent<T>
{
  protected final INameFactory    nameFactory_;

  protected List<Subscription<P>> subscribers_ = new ArrayList<>();
  
  protected AbstractSubscriberBase(INameFactory nameFactory, Class<T> type)
  {
    super(type);
    
    nameFactory_ = nameFactory;
  }

  @Deprecated
  protected T withSubscription(@Nullable IThreadSafeRetryableConsumer<P> consumer, String subscriptionId, String topicId,
      String... additionalTopicIds)
  {
    assertConfigurable();
    
    subscribers_.add(new Subscription<P>(
        nameFactory_.getTopicNameCollection(topicId, additionalTopicIds),
        nameFactory_.getObsoleteTopicNameCollection(topicId, additionalTopicIds),
        null,
        subscriptionId, consumer));
    
    return self();
  }

  protected T withSubscription(@Nullable IThreadSafeRetryableConsumer<P> consumer, String subscriptionId, Collection<TopicName> topicNames)
  {
    assertConfigurable();
    
    if(topicNames.isEmpty())
      throw new IllegalArgumentException("At least one topic name is required");
    
    subscribers_.add(new Subscription<P>(topicNames, subscriptionId, consumer));
    
    return self();
  }
  
  protected T withSubscription(@Nullable IThreadSafeRetryableConsumer<P> consumer, String subscriptionId, String[] topicIds)
  {
    assertConfigurable();
    
    if(topicIds==null || topicIds.length==0)
      throw new IllegalArgumentException("At least one topic name is required");
    
    List<TopicName> topicNameList = new ArrayList<>(topicIds.length);
    List<TopicName> obsoleteTopicNameList = new ArrayList<>(topicIds.length);

    for(String id : topicIds)
    {
      topicNameList.add(nameFactory_.getTopicName(id));
      obsoleteTopicNameList.add(nameFactory_.getObsoleteTopicName(id));
    }
    
    subscribers_.add(new Subscription<P>(topicNameList, obsoleteTopicNameList, null, subscriptionId, consumer));
    
    return self();
  }

  protected List<Subscription<P>> getSubscribers()
  {
    return subscribers_;
  }
}
