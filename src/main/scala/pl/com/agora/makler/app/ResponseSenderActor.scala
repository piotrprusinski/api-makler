package pl.com.agora.makler.app

import akka.actor.Actor
import akka.actor.ActorRef
import spray.http.HttpResponse
import spray.routing.RequestContext

class ResponseSenderActor(ctx: RequestContext) extends Actor {
  
  ctx.complete("ok from actor")
  
  def receive: Receive = {
    case _ => "nope"
  }
  
}