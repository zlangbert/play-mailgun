# play-mailgun

play-mailgun is a play plugin to make using the Mailgun API from Scala simple. The plugin requires Play 2.4.

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