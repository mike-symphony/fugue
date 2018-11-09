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

package org.symphonyoss.s2.fugue.core.trace.log;

import java.time.Instant;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.symphonyoss.s2.common.hash.Hash;
import org.symphonyoss.s2.common.hash.HashProvider;
import org.symphonyoss.s2.fugue.core.trace.ITraceContext;
import org.symphonyoss.s2.fugue.core.trace.ITraceContextTransaction;

class LoggerTraceContext implements ITraceContext
{
  private static final Logger             log_       = LoggerFactory.getLogger(LoggerTraceContext.class);

  private final String                    subjectType_;
  private final String                    subjectId_;
  private final Hash                      hash_;
  private final LoggerTraceContextTransactionFactory factory_;
  private final String                    parentHash_;
  private final Instant                   timestamp_;
  private final Hash                      id_        = HashProvider.getCompositeHashOf(UUID.randomUUID());

  private final long                      start_     = System.currentTimeMillis();
  private long                            lastEvent_ = start_;
  
  LoggerTraceContext(LoggerTraceContextTransactionFactory factory, Hash parentHash, String subjectType, String subjectId)
  {
    factory_ = factory;
    parentHash_ = parentHash == null ? "" : parentHash.toString();
    subjectType_ = subjectType;
    subjectId_ = subjectId;
    hash_ = HashProvider.getCompositeHashOf(id_, subjectType_, subjectId_);
    
    trace("STARTED");
    
    timestamp_ = Instant.now();
  }

  @Override
  public Hash getHash()
  {
    return hash_;
  }

  @Override
  public void trace(String operationId)
  {
    trace(operationId, "", "");
  }

  @Override
  public void trace(String operationId, String subjectType, String subjectId)
  {
    long now = System.currentTimeMillis();
    long operation = now - lastEvent_;
    long total = now - start_;
    
    lastEvent_ = now;
    
    log_.debug(String.format("TRACE %-50.50s %-50.50s %-20.20s %5d %5d %-30.30s %-40.40s %-20.20s %s", parentHash_, id_, operationId, operation, total, 
        subjectType_, subjectId_, subjectType, subjectId));
  }

  @Override
  public ITraceContextTransaction createSubContext(String externalSubjectType, String externalSubjectId)
  {
    factory_.increment(externalSubjectType);
    
    return new LoggerTraceContextTransaction(factory_, hash_, externalSubjectType, externalSubjectId);
  }

  @Override
  public void trace(String operationId, Instant time)
  {
    trace(operationId);
  }

  @Override
  public ITraceContextTransaction createSubContext(String externalSubjectType, String externalSubjectId, Instant time)
  {
    return createSubContext(externalSubjectType, externalSubjectId);
  }

  @Override
  public Instant getTimestamp()
  {
    return timestamp_;
  }
}
