package pl.com.agora.makler.app.actors


import akka.actor.Actor
import akka.actor.ActorLogging
import akka.actor.ActorRef
import akka.actor.Props
import pl.com.agora.makler.app.messages.HandleRequest
import pl.com.agora.makler.app.messages.BackendResponse

class BackendRequestReceiver extends Actor with ActorLogging {

  def receive  = {
    case msg : BackendResponse => {
      log.debug("Handling message : {}", msg.msg)
      getResponseSender(msg) ! msg
    }
  }
  
  
  def getResponseSender (msg : BackendResponse):ActorRef = {
    msg.msg.responseSender
  }
  

}