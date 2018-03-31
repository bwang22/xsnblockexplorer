package com.xsn.explorer.modules

import com.google.inject.AbstractModule
import com.xsn.explorer.config.{MongoConfig, PlayMongoConfig, PlayRPCConfig, RPCConfig}

class ConfigModule extends AbstractModule {

  override def configure(): Unit = {
    bind(classOf[RPCConfig]).to(classOf[PlayRPCConfig])
    bind(classOf[MongoConfig]).to(classOf[PlayMongoConfig])
  }
}
