package com.zlangbert.mailgun

import java.io.File

sealed trait Attachment

case class AttachmentFile(file: File) extends Attachment

case class AttachmentData(name: String,
                          data: Array[Byte],
                          mimeType: String) extends Attachment