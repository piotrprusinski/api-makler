package pl.com.agora.makler.app.messages

import akka.actor.ActorRef
import spray.http.HttpRequest

class MatchRequest(val url: String, val responseSender: ActorRef,val request: HttpRequest) {

}