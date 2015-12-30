package pl.com.agora.makler.app.actors

import akka.actor.Actor
import akka.actor.ActorLogging
import pl.com.agora.makler.app.messages.HandleRequest
import akka.actor.ActorRef
import akka.actor.Props

class SecureResponseCache extends Actor with ActorLogging {

  def receive  = {
    
    case msg : HandleRequest => {
      log.debug("Handling LearnApiKey message : {}", msg)
      getNewInstanceBackendRequestServer()! msg
      
    }
    
  }
  
    def getNewInstanceBackendRequestServer ():ActorRef = {
    context.actorOf(Props[BackendRequestSender])
  }
  
}