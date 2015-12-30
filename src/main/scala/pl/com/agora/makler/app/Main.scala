package pl.com.agora.makler.app

import akka.actor._
import akka.io.IO
import akka.util.Timeout
import com.typesafe.config.ConfigFactory
import spray.can.Http
import scala.concurrent.duration._

object Main extends App {
  val config = ConfigFactory.load()
  val host = config.getString("http.host")
  val port = config.getInt("http.port")

  implicit val system = ActorSystem("makler-service")

  val api = system.actorOf(Props(new RestInterface()), "httpInterface")

  implicit val executionContext = system.dispatcher
  implicit val timeout = Timeout(10 seconds)

  IO(Http) ! (Http.Bind(listener = api, interface = host, port = port))
}
