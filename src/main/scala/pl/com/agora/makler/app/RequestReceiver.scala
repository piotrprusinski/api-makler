package pl.com.agora.makler.app

import spray.httpx.marshalling.ToResponseMarshallable.isMarshallable
import spray.routing.Directives
import spray.routing.HttpServiceActor
import spray.routing.Route
import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.ActorRefFactory
import spray.routing.RequestContext

class RequestReceiver(route: Route) extends HttpServiceActor {
  def receive: Receive = runRoute(route)
}

object RequestReceiver extends Directives {

  def route(system: ActorRefFactory): Route = {
    (ctx : RequestContext) => sendResponse(system, ctx)
  
  }
  
  def sendResponse(actorRefFactory: ActorRefFactory, ctx: RequestContext): Unit = {
    actorRefFactory.actorOf(Props(new ResponseSenderActor(ctx)))
  }
}