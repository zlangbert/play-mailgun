package com.zlangbert.mailgun

import play.api.Logger

class MockMailer extends MailerClient {

  /**
   * @inheritdoc
   */
  override def send(email: Email): String = {
    Logger.info("mock implementation, send email")
    Logger.info(s"subject: ${email.subject}")
    Logger.info(s"from: ${email.from}")
    email.bodyText.foreach(bodyText => Logger.info(s"bodyText: $bodyText"))
    email.bodyHtml.foreach(bodyHtml => Logger.info(s"bodyHtml: $bodyHtml"))
    email.to.foreach(to => Logger.info(s"to: $to"))
    email.cc.foreach(cc => Logger.info(s"cc: $cc"))
    email.bcc.foreach(bcc => Logger.info(s"to: $bcc"))
    email.replyTo.foreach(replyTo => Logger.info(s"replyTo: $replyTo"))
    email.attachments.foreach(attachment => Logger.info(s"attachment: $attachment"))
    email.headers.foreach(header => Logger.info(s"header: $header"))
    ""
  }
}