package pl.com.agora.makler.app

import com.typesafe.config.ConfigFactory
import akka.actor._
import akka.io.IO
import akka.util.Timeout
import pl.com.agora.makler.app.actors.RequestReceiver
import spray.can.Http

object Main extends App {
  val config = ConfigFactory.load()
  val host = config.getString("http.host")
  val port = config.getInt("http.port")

  implicit val system = ActorSystem("makler-service")

  val api = system.actorOf(Props(new RequestReceiver(RequestReceiver.route(system))), "handler")

  /*implicit val executionContext = system.dispatcher
  implicit val timeout = Timeout(10 seconds)
*/
  IO(Http) ! (Http.Bind(listener = api, interface = host, port = port))
}
