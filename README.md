# play-mailgun

play-mailgun is a play plugin to make using the Mailgun API from Scala simple. The plugin requires Play 2.4.

## Usage

Depend on the library:

```
"com.zlangbert" %% "play-mailgun" % "0.1.0"
```

add your domain and api key to application your config:
 
```
mailer {
  domain = "mg.mydomain.com"
  apiKey = "myapikey"
}
```

inject a `MailerClient` into your controller:

```scala
class Application @Inject() (mailer: MailerClient) {}
```

send an email:

```scala
val email = Email(...)
mailer.send(email)
```

## Todo

Currently this plugin uses the Jersey http client. Originally I wanted to use akka-http but unfortunatley it isn't currently possible to make an HTTPS request. Once the akka-http client is a little further along I intend to switch to that.
