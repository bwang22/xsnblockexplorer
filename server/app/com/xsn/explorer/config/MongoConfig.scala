package com.xsn.explorer.config

import javax.inject.Inject

import play.api.Configuration

trait MongoConfig {

  import ConfigKeys._

  def uri: Uri
}

class PlayMongoConfig @Inject() (config: Configuration) extends MongoConfig {

  import ConfigKeys._

  private def get(name: String) = config.get[String](s"mongo.$name")

  override val uri: Uri = Uri(get("uri"))

}
