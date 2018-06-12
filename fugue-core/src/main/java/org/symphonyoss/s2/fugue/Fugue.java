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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.symphonyoss.s2.common.fault.ProgramFault;

/**
 * Fugue constants and global utility methods.
 * 
 * @author Bruce Skingle
 *
 */
public class Fugue
{
  /** 
   * Environment variable containing a pointer to the configuration.
   * 
   * This configuration is a common view of configuration seen by all processes in an environment.
   */
  public static final String FUGUE_CONFIG = "FUGUE_CONFIG";
 
  /**
   * Environment variable containing an optional pointer to the task configuration.
   * 
   * A task configuration is a set of instructions describing the reuired actions. These
   * are usually needed only for administration tasks such as deploying new instances etc.
   */
  public static final String FUGUE_TASK = "FUGUE_TASK";
 
  /**
   * Environment variable containing a pointer to the sub-tree in the configuration which applies
   * to the current process. 
   */
  public static final String FUGUE_ID = "FUGUE_ID";
  
  /** Environment variable containing the process instance ID */
  public static final String FUGUE_INSTANCE = "FUGUE_INSTANCE";
  
  /**
   * Get the requested value as a System Property or environment variable.
   * 
   * @param name  The name of the required property.
   * @return      The value of the property or <code>null</code>
   */
  public static @Nullable String  getProperty(String name)
  {
    String value = System.getProperty(name);
    
    if(value == null)
      value = System.getenv(name);
    
    return value;
  }
  
  /**
   * Get the requested value as a System Property or environment variable.
   * 
   * A ProgramFault is thrown if the value does not exist so the return value is guaranteed to be non-null.
   * 
   * @param name  The name of the required property.
   * @return      The value of the property.
   * 
   * @throws      ProgramFault if the property is undefined.
   */
  public static @Nonnull String  getRequiredProperty(String name)
  {
    String value = getProperty(name);
    
    if(value == null)
      throw new ProgramFault("\"" + name + "\" must be set as a system property or environment variable.");
    
    return value;
  }
}
