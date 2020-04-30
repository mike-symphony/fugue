/*
 *
 *
 * Copyright 2019 Symphony Communication Services, LLC.
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

package org.symphonyoss.s2.fugue.inmemory.assembly;

import org.symphonyoss.s2.common.fault.FaultAccumulator;
import org.symphonyoss.s2.common.fluent.BaseAbstractBuilder;
import org.symphonyoss.s2.fugue.IFugeComponentContainer;
import org.symphonyoss.s2.fugue.IFugueAssembly;
import org.symphonyoss.s2.fugue.config.GlobalConfiguration;
import org.symphonyoss.s2.fugue.config.IGlobalConfiguration;
import org.symphonyoss.s2.fugue.inmemory.InMemoryConfiguration;
import org.symphonyoss.s2.fugue.naming.INameFactory;
import org.symphonyoss.s2.fugue.naming.NameFactory;

/**
 * A base assembly for In-Memory implementations.
 * 
 * @author Bruce Skingle
 *
 */
public class InMemoryFugueAssembly implements IFugueAssembly
{
  protected final IFugeComponentContainer<?> container_;
  protected final IGlobalConfiguration       config_;
  protected final INameFactory               nameFactory_;
  
  protected InMemoryFugueAssembly(AbstractBuilder<?,?> builder)
  {
    container_                = builder.container_;
    config_                   = builder.config_;
    nameFactory_              = builder.nameFactory_;
  }
  
  protected <C> C register(C component)
  {
    return container_.register(component);
  }
  
  protected static abstract class AbstractBuilder<T extends AbstractBuilder<T,B>, B extends InMemoryFugueAssembly>
  extends BaseAbstractBuilder<T, B>
  {
    protected IGlobalConfiguration              config_;
    protected INameFactory                      nameFactory_;

    protected IFugeComponentContainer<?>        container_;
    
    public AbstractBuilder(Class<T> type)
    {
      super(type);
    }

    public T withConfiguration(IGlobalConfiguration config)
    {
      config_ = config;
      
      return self();
    }
    
    public IGlobalConfiguration getConfiguration()
    {
      return config_;
    }

    public INameFactory getNameFactory()
    {
      return nameFactory_;
    }

    public T withContainer(IFugeComponentContainer<?> container)
    {
      container_ = container;
      
      return self();
    }

    @Override
    public void validate(FaultAccumulator faultAccumulator)
    {
      super.validate(faultAccumulator);
      
      faultAccumulator.checkNotNull(container_, "container");
      
      if(config_ == null)
        config_ = new GlobalConfiguration(InMemoryConfiguration.FACTORY.newInstance());
      
      if(nameFactory_ == null)
        nameFactory_ = new NameFactory(config_);
      
    }
  }
}
