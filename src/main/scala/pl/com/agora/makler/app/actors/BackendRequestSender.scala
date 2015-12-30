package pl.com.agora.makler.app.actors

import pl.com.agora.makler.app.messages.BackendResponse

import akka.actor.Actor
import akka.actor.ActorLogging
import akka.actor.ActorRef
import akka.actor.Props
import pl.com.agora.makler.app.messages.HandleRequest
import pl.com.agora.makler.app.messages.BackendResponse

class BackendRequestSender extends Actor with ActorLogging {


  def receive  = {
    
    case msg : HandleRequest => {
      log.debug("Handling LearnApiKey message : {}", msg)
      getNewInstanceBackendRequestReciver() //! new BackendResponse(msg)
      context.stop(self)
    }
  }
  
   def getNewInstanceBackendRequestReciver ():ActorRef = {
    context.actorOf(Props[BackendRequestReceiver])
  }

  override def postStop = {
      log.debug("post selfkill")
    }   
   
}