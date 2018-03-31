package com.xsn.explorer.config

object ConfigKeys {

  case class Host(string: String) extends AnyVal
  case class Username(string: String) extends AnyVal
  case class Password(string: String) extends AnyVal
  case class Uri(string: String) extends AnyVal
}
