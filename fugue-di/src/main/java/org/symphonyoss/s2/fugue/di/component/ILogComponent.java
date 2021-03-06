/*
 *
 *
 * Copyright 2017 Symphony Communication Services, LLC.
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

package org.symphonyoss.s2.fugue.di.component;

import org.symphonyoss.s2.fugue.di.IComponent;

/**
 * A logging component.
 * 
 * This is intended to allow you to get the DIContext to log to the logger
 * of your choice rather than as a way for your own application classes
 * to get logger instances.
 * 
 * There is no reason why you could not implement a logger factory component
 * but there is also no reason why you can't do logging the way you usually
 * do too.
 * 
 * An example of an implementation using SLF4J is included in the examples.
 * 
 * @author Bruce Skingle
 *
 */
public interface ILogComponent extends IComponent
{
  /**
   * Log a message with a stack trace at DEBUG level.
   * 
   * @param message A message to be logged.
   * @param cause   A Throwable whose stack trace should be logged.
   */
  void debug(Object message, Throwable cause);

  /**
   * Log a message at DEBUG level.
   * 
   * @param message A message to be logged.
   */
  void debug(Object message);

  /**
   * Log a message with a stack trace at INFO level.
   * 
   * @param message A message to be logged.
   * @param cause   A Throwable whose stack trace should be logged.
   */
  void info(Object message, Throwable cause);

  /**
   * Log a message at INFO level.
   * 
   * @param message A message to be logged.
   */
  void info(Object message);

  /**
   * Log a message with a stack trace at WARN level.
   * 
   * @param message A message to be logged.
   * @param cause   A Throwable whose stack trace should be logged.
   */
  void warn(Object message, Throwable cause);

  /**
   * Log a message at WARN level.
   * 
   * @param message A message to be logged.
   */
  void warn(Object message);

  /**
   * Log a message with a stack trace at ERROR level.
   * 
   * @param message A message to be logged.
   * @param cause   A Throwable whose stack trace should be logged.
   */
  void error(Object message, Throwable cause);

  /**
   * Log a message at ERROR level.
   * 
   * @param message A message to be logged.
   */
  void error(Object message);

}
