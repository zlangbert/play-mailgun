package com.zlangbert.mailgun

case class Email(subject: String,
                 from: String,
                 to: Seq[String] = Seq.empty,
                 bodyText: Option[String] = None,
                 bodyHtml: Option[String] = None,
                 cc: Seq[String] = Seq.empty,
                 bcc: Seq[String] = Seq.empty,
                 attachments: Seq[Attachment] = Seq.empty)