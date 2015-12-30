package pl.com.agora.makler.app.messages

import pl.com.agora.makler.app.actors.ApiKey
import akka.actor.ActorRef

class HandleRequest(val apiKey:ApiKey, val responseSender : ActorRef) {
  
    override def toString() : String = {
    "HandleRequest with apiKey: " + apiKey.apiKey
  }
  
}