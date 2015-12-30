package pl.com.agora.makler.app.actors

import akka.actor.{ActorRefFactory, Props}
import pl.com.agora.makler.app.actors.ResponseSender
import spray.routing.{Directives, HttpServiceActor, RequestContext, Route}

class RequestReceiver(route: Route) extends HttpServiceActor {
  def receive: Receive = runRoute(route)
}

object RequestReceiver extends Directives {

  def route(system: ActorRefFactory): Route = {
    (ctx : RequestContext) => sendResponse(system, ctx)
  
  }
  
  def sendResponse(actorRefFactory: ActorRefFactory, ctx: RequestContext): Unit = {
    actorRefFactory.actorOf(Props(new ResponseSender(ctx)))
  }
}