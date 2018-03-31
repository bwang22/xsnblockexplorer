package com.xsn.explorer.config

import javax.inject.Inject

import play.api.Configuration

trait RPCConfig {

  import ConfigKeys._

  def host: Host
  def username: Username
  def password: Password
}

class PlayRPCConfig @Inject() (config: Configuration) extends RPCConfig {

  import ConfigKeys._

  private def get(name: String) = config.get[String](s"rpc.$name")

  override val host: Host = Host(get("host"))

  override def username: Username = Username(get("username"))

  override def password: Password = Password(get("password"))

}
