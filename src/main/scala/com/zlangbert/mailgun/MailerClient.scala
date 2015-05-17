package com.zlangbert.mailgun

trait MailerClient {

  /**
   * Sends an email
   * @param email The email to send
   * @return The message id
   */
  def send(email: Email): String
}