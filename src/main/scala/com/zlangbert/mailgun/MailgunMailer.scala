package com.zlangbert.mailgun

import java.io.ByteArrayInputStream
import java.net.URI
import javax.ws.rs.client.{ClientBuilder, Entity}
import javax.ws.rs.core.MediaType

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature
import org.glassfish.jersey.media.multipart.{MultiPartFeature, FormDataMultiPart}
import org.glassfish.jersey.media.multipart.file.{FileDataBodyPart, StreamDataBodyPart}
import play.api.libs.json._

class MailgunMailer(domain: String, apiKey: String) extends MailerClient {

  val apiUrl = new URI("https://api.mailgun.net/v3/")

  private lazy val client = {
    val c = ClientBuilder.newClient()
    c.register(classOf[MultiPartFeature])
    val auth = HttpAuthenticationFeature.basic("api", apiKey)
    c.register(auth, 1)
  }

  /**
   * @inheritdoc
   */
  override def send(email: Email): String = {
    val request = client.target(apiUrl).path(domain).path("messages").request()
    val data = convert(email)
    val response = request.post(data)
    val json = Json.parse(response.readEntity(classOf[String]))
    response.getStatus match {
      case 200 =>
        (json \ "id").asOpt[String].getOrElse(throw new MailgunMailerException("Failed to retrieve message id"))
      case _ =>
        val error = (json \ "message").asOpt[String]
          .getOrElse(throw new MailgunMailerException("Error sending message: error message not found"))
        throw new MailgunMailerException(s"Error sending message: $error")
    }
  }

  private def convert(email: Email): Entity[_] = {
    val form = new FormDataMultiPart()
    form.field("from", email.from)
    email.to.foreach(form.field("to", _))
    email.cc.foreach(form.field("cc", _))
    email.bcc.foreach(form.field("bcc", _))
    form.field("subject", email.subject)
    email.bodyHtml.filter(_.trim.nonEmpty).foreach(form.field("html", _))
    email.bodyText.filter(_.trim.nonEmpty).foreach(form.field("text", _))
    email.attachments.foreach {
      case AttachmentFile(file) =>
        val bodyPart = new FileDataBodyPart("attachment", file)
        form.bodyPart(bodyPart)
      case AttachmentData(name, data, mt) =>
        val bs = new ByteArrayInputStream(data)
        val bodyPart = new StreamDataBodyPart("attachment", bs, name, mimeType(mt))
        form.bodyPart(bodyPart)
    }

    Entity.entity(form, form.getMediaType)
  }

  val mimeTypePattern = """(\w+)/(\w+)""".r
  private def mimeType(s: String): MediaType = {
    s match {
      case mimeTypePattern(tpe, subType) => new MediaType(tpe, subType)
      case _ => throw new MailgunMailerException("Unexpected MIME type format")
    }
  }
}