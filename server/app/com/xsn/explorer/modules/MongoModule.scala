package com.xsn.explorer.modules

import javax.inject.Singleton

import com.google.inject.{AbstractModule, Provides}
import com.xsn.explorer.config.MongoConfig
import org.mongodb.scala.{MongoClient, MongoDatabase}

class MongoModule extends AbstractModule {

  override def configure(): Unit = {}

  @Singleton
  @Provides
  def mongoClient(config: MongoConfig): MongoClient = {
    MongoClient.apply(config.uri.string)
  }

  @Singleton
  @Provides
  def mongoDatabase(mongoClient: MongoClient): MongoDatabase = {
    mongoClient.getDatabase("blockchain")
  }
}
