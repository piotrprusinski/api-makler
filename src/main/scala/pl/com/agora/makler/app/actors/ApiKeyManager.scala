package pl.com.agora.makler.app.actors

import akka.actor.Actor
import collection.mutable.Set
import pl.com.agora.makler.app.messages.HandleRequest
import pl.com.agora.makler.app.messages.LearnApiKey
import akka.actor.ActorLogging
import akka.actor.ActorSystem
import akka.actor.ActorRef
import akka.actor.Props

class ApiKeyManager extends Actor {
  
  val knownApiKeys : Set[ApiKey] = Set()
  
  def receive = { 
    case msg : HandleRequest => {
      //log.error("case 1")
      println ("case 1")
      if (known(msg.apiKey)){
        println ("znam: "+msg.apiKey)
        
        getKnownKeyMessageHandler() ! msg
        //context.actorOf(props)
        
      }else{
        println ("nie znam: "+msg.apiKey)
        getUnknownKeyMessageHandler() ! msg
      }
    }
    
    case msg : LearnApiKey => {
      println ("case 2")
      knownApiKeys+=msg.apiKey
      println("zapamietuje: "+msg.apiKey)
    }
  }
  
  
  
  def known(apiKey:ApiKey):Boolean = {
    println("czy znam?: "+apiKey)
    knownApiKeys(apiKey)
  }
  
  def getUnknownKeyMessageHandler ():ActorRef = {
    context.actorOf(Props[DumyActor])
    
  }
  
  def getKnownKeyMessageHandler ():ActorRef = {
    context.actorOf(Props[DumyActor])
  }
  
}