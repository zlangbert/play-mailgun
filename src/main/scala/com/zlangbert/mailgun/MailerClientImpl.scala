package com.zlangbert.mailgun

import javax.inject.Inject

import play.api.Configuration

class MailerClientImpl @Inject() (configuration: Configuration) extends MailerClient {

  private lazy val instance = {
    if (configuration.getBoolean("mailer.mock").getOrElse(false)) {
      new MockMailer
    } else {
      val domain = configuration.getString("mailer.domain")
        .getOrElse(throw new RuntimeException("mailer.domain must be set in order to use the mailgun plugin"))
      val apiKey = configuration.getString("mailer.apiKey")
        .getOrElse(throw new RuntimeException("mailer.apiKey must be set in order to use the mailgun plugin"))
      new MailgunMailer(domain, apiKey)
    }
  }

  /**
   * @inheritdoc
   */
  override def send(email: Email): String = instance.send(email)
}