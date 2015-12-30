package pl.com.agora.makler.app.actors

import akka.actor.Actor
import akka.actor.ActorLogging

class DumyActor extends Actor with ActorLogging{
  
  def receive  = {
    case msg => log.debug("msg: {}", msg ) 
  }
  
}