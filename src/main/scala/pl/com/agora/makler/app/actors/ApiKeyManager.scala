package pl.com.agora.makler.app.actors

import akka.actor.Actor
import collection.mutable.Set
import pl.com.agora.makler.app.messages.HandleRequest
import pl.com.agora.makler.app.messages.LearnApiKey
import akka.actor.ActorLogging
import akka.actor.ActorSystem
import akka.actor.ActorRef
import akka.actor.Props

class ApiKeyManager extends Actor with ActorLogging {
  
  val knownApiKeys : Set[ApiKey] = Set()
  
  def receive = { 
    case msg : HandleRequest => {
      log.debug("Handling HandleRequest message : {}", msg)
      if (known(msg.apiKey)){
        log debug ("I know this apiKey : {}", msg.apiKey)        
        getKnownKeyMessageHandler() ! msg        
      }else{
      	log.debug("I don't know this apiKey : {}", msg.apiKey)
        getUnknownKeyMessageHandler() ! msg
      }
    }
    
    case msg : LearnApiKey => {
      log.debug("Handling LearnApiKey message : {}", msg)    	
      remember(msg.apiKey)
    }
  }
  
  def remember(apiKey: ApiKey) : Unit = knownApiKeys += apiKey;
  
  def known(apiKey:ApiKey):Boolean = {
    knownApiKeys(apiKey)
  }
  
  def getUnknownKeyMessageHandler ():ActorRef = {
    context.actorOf(Props[DumyActor])
  }
  
  def getKnownKeyMessageHandler ():ActorRef = {
    context.actorOf(Props[SecureResponseCache])
  }
  
}