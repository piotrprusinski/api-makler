package pl.com.agora.makler.app.actors

import scala.concurrent.duration.DurationInt
import akka.actor.Actor
import akka.actor.ActorLogging
import akka.actor.Props
import pl.com.agora.makler.app.messages.MatchRequest
import pl.com.agora.makler.app.messages.SetTimeout
import pl.com.agora.makler.app.messages.HandleRequest

class UrlMatcher extends Actor with ActorLogging {

  val pattern = List(new RouteConfig("/", "http://api.gazeta.pl/quiz/v1/4530.servlet?apikey=ef27fa575d9d4d23803f4437c7a3cc75", 20))

  def receive: Receive = {
    
    case msg:MatchRequest =>
      pattern.foreach { o => {
        if(msg.url.contains(o.pattern)){
          msg.responseSender ! new SetTimeout(new DurationInt(o.timeout).seconds)
         var akm = context.system.actorOf(Props[ApiKeyManager])
         akm ! new HandleRequest(new ApiKey("ef27fa575d9d4d23803f4437c7a3cc75"), msg.responseSender)
        }
      }}
    case _ =>
      log.debug("receinve message")
  }
}

class RouteConfig(val pattern: String,val url: String, val timeout: Int)