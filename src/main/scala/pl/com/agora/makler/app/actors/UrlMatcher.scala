package pl.com.agora.makler.app.actors

import akka.actor.Actor
import akka.actor.ActorLogging

class UrlMatcher extends Actor with ActorLogging {

  val pattern = List(new RouteConfig("*", "http://api.gazeta.pl/quiz/v1/4530.servlet?apikey=ef27fa575d9d4d23803f4437c7a3cc75", 10000))

  def receive: Receive = {
    case _ => "ok"
  }
}

class RouteConfig(pattern: String, url: String, timeout: Int)