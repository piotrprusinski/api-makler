package pl.com.agora.makler.app.messages

import akka.actor.ActorRef

class MatchRequest(val url: String, val responseSender: ActorRef) {

}