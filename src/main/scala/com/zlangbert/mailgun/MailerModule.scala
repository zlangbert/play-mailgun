package com.zlangbert.mailgun

import play.api.{Configuration, Environment}
import play.api.inject._

class MailerModule extends Module {
  override def bindings(environment: Environment, configuration: Configuration) = Seq(
    bind[MailerClient].to[MailerClientImpl],
    bind[MailerClient].qualifiedWith("mock").to[MockMailer]
  )
}