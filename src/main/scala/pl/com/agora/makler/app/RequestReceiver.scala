package pl.com.agora.makler.app

import spray.httpx.marshalling.ToResponseMarshallable.isMarshallable
import spray.routing.Directives
import spray.routing.HttpServiceActor
import spray.routing.Route
import akka.actor.ActorSystem

class RequestReceiver(route: Route) extends HttpServiceActor {
  def receive: Receive = runRoute(route)
}

object RequestReceiver extends Directives {

  def route(system: ActorSystem): Route = {
    complete {
      "ok"
    }
  }
}