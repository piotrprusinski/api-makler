package pl.com.agora.makler.app.actors

import akka.actor.Actor

class DumyActor extends Actor{
  
  def receive  = {
    case msg => println ("msg:"+ msg ) 
  }
  
}