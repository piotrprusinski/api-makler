package pl.com.agora.makler.app.actors

import akka.actor.Actor
import akka.actor.ActorLogging
import akka.actor.ActorRef
import akka.actor.Props
import pl.com.agora.makler.app.messages.HandleRequest

class BackendRequestSender extends Actor with ActorLogging {

  def receive  = {
    
    case msg : HandleRequest => {
      log.debug("Handling LearnApiKey message : {}", msg)
    }
  }
}