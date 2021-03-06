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

package org.symphonyoss.s2.fugue;

import org.symphonyoss.s2.common.fluent.IFluent;

/**
 * A Fluent base class for Fugue Lifecycle Components.
 * 
 * @author Bruce Skingle
 *
 * @param <T> The concrete type to be returned by fluent methods.
 */
public abstract class FugueLifecycleComponent<T extends IFluent<T>> extends FugueLifecycleBase<T> implements IFugueLifecycleComponent
{
  private FugueComponentState componentState_         = FugueComponentState.OK;
  private String              componentStatusMessage_ = "OK";

  /**
   * Constructor.
   * 
   * @param type The concrete type returned by fluent methods.
   */
  public FugueLifecycleComponent(Class<T> type)
  {
    super(type);
  }

  @Override
  public FugueComponentState getComponentState()
  {
    return componentState_;
  }

  @Override
  public String getComponentStatusMessage()
  {
    return componentStatusMessage_;
  }

  protected void setComponentState(FugueComponentState componentState)
  {
    componentState_ = componentState;
  }

  protected void setComponentStatusMessage(String componentStatusMessage)
  {
    componentStatusMessage_ = componentStatusMessage;
  }
}
