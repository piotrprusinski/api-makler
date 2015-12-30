package pl.com.agora.makler.app.actors


import akka.actor.Actor
import akka.actor.ActorLogging
import akka.actor.ActorRef
import akka.actor.Props
import pl.com.agora.makler.app.messages.HandleRequest
import pl.com.agora.makler.app.messages.BackendResponse
import scala.concurrent.duration._
import akka.actor.ReceiveTimeout

class BackendRequestReceiver extends Actor with ActorLogging {
context.setReceiveTimeout(scala.concurrent.duration.Duration(1,SECONDS))
  
  def receive  = {
    case msg : ReceiveTimeout =>{
      log.debug("Self kill on timeout")
      context.stop(self)
    }
  
    case msg : BackendResponse => {
      log.debug("Handling message : {}", msg.msg)
      log.debug("self : {}", self)
      getResponseSender(msg) ! msg
      context.stop(self)
    }
  }
  
  
  def getResponseSender (msg : BackendResponse):ActorRef = {
    msg.msg.responseSender
  }
  
  override def postStop = {
    log.debug("post selfkill")
  }
  

}