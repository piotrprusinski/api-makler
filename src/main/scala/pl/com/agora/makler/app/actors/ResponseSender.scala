package pl.com.agora.makler.app.actors

import akka.actor.Actor
import spray.routing.RequestContext

class ResponseSender(ctx: RequestContext) extends Actor {
  
  ctx.complete("ok from actor")
  
  def receive: Receive = {
    case _ => "nope"
  }
  
}