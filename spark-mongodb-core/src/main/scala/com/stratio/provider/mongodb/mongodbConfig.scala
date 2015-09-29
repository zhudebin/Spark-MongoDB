/*
 *  Licensed to STRATIO (C) under one or more contributor license agreements.
 *  See the NOTICE file distributed with this work for additional information
 *  regarding copyright ownership. The STRATIO (C) licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License. You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied. See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package com.stratio.provider.mongodb

import com.mongodb.{MongoClientOptions => JavaMongoClientOptions}
import com.stratio.provider.Config._
import com.stratio.provider.ConfigBuilder
import com.stratio.provider.mongodb.MongodbConfig._

/**
 * Created by jsantos on 5/02/15.
 *
 * A specialized Mongo configuration builder.
 * It focuses on mongodb config parameters
 * such as host,database,collection, samplingRatio (for schema infer)
 * @param props Initial properties map
 */
case class MongodbConfigBuilder(
                                 props: Map[Property, Any] = Defaults
                                 ) extends {
  override val properties = Defaults ++ props
} with ConfigBuilder[MongodbConfigBuilder](properties) {

  val requiredProperties: List[Property] = MongodbConfig.all

  def apply(props: Map[Property, Any]) =
    MongodbConfigBuilder(mapClientOptions(Defaults ++ props))

   /**
   * Create a new Map with mongoClientOptions in one element Map.
   +
   * @param props Initial properties map
   * @return Map[Property, Any]
   */
  def mapClientOptions(props: Map[Property, Any]) : Map[Property, Any] =
    props ++ Map ( MongodbConfig.ClientOptions -> MongodbConfig.ListMongoClientOptions.map(opt => opt -> props(opt).toString).toMap)

}

object MongodbConfig {

  //  Parameter names

  val Host = "host"
  val Database = "database"
  val Collection = "collection"
  val SamplingRatio = "schema_samplingRatio"
  val WriteConcern = "writeConcern"
  val SplitSize = "splitSize"
  val SplitKey = "splitKey"
  val AllowSlaveReads = "allowSlaveReads"
  val Credentials = "credentials"
  val IdField = "_idField"
  val SearchFields = "searchFields"
  val SSLOptions = "sslOptions"
  val Language = "language"

  val ClientOptions = "clientOptions"
  val ReadPreference = "readPreference"
  val ConnectTimeout = "connectTimeout"
  val ConnectionsPerHost = "connectionsPerHost"
  val MaxWaitTime = "maxWaitTime"
  val SocketTimeout = "socketTimeout"
  val ThreadsAllowedToBlockForConnectionMultiplier = "threadsAllowedToBlockForConnectionMultiplier"

  // List of parameters for mongoclientoption
  val ListMongoClientOptions = List(
    ReadPreference,
    ConnectionsPerHost,
    ConnectTimeout,
    MaxWaitTime,
    ThreadsAllowedToBlockForConnectionMultiplier
  )

  /**
   * Mandatory
   */
  val all = List(
    Host,
    Database,
    Collection,
    SamplingRatio,
    WriteConcern,
    SplitKey,
    SplitSize,
    ReadPreference,
    ConnectionsPerHost,
    ConnectTimeout,
    MaxWaitTime,
    ThreadsAllowedToBlockForConnectionMultiplier
    )

  //  Default values

  type Builder = JavaMongoClientOptions.Builder
  val DefaultMongoClientOptions = new JavaMongoClientOptions.Builder().build()

  val DefaultSamplingRatio = 1.0
  val DefaultWriteConcern = DefaultMongoClientOptions.getWriteConcern
  val DefaultSplitKey = "_id"
  val DefaultSplitSize = 10
  val DefaultAllowSlaveReads = false
  val DefaultCredentials = List[MongodbCredentials]()
  val DefaultReadPreference = "nearest"
  val DefaultConnectTimeout = DefaultMongoClientOptions.getConnectTimeout

  val DefaultConnectionsPerHost = DefaultMongoClientOptions.getConnectionsPerHost
  val DefaultMaxWaitTime = DefaultMongoClientOptions.getMaxWaitTime
  val DefaultSocketTimeout = DefaultMongoClientOptions.getSocketTimeout
  val DefaultThreadsAllowedToBlockForConnectionMultiplier= DefaultMongoClientOptions.getThreadsAllowedToBlockForConnectionMultiplier

  val Defaults = Map(
    SamplingRatio -> DefaultSamplingRatio,
    WriteConcern -> DefaultWriteConcern,
    SplitKey -> DefaultSplitKey,
    SplitSize -> DefaultSplitSize,
    AllowSlaveReads -> DefaultAllowSlaveReads,
    Credentials -> DefaultCredentials,
    ReadPreference-> DefaultReadPreference,
    ConnectTimeout -> DefaultConnectTimeout,
    ConnectionsPerHost -> DefaultConnectionsPerHost,
    MaxWaitTime -> DefaultMaxWaitTime,
    ThreadsAllowedToBlockForConnectionMultiplier -> DefaultThreadsAllowedToBlockForConnectionMultiplier
    )
}