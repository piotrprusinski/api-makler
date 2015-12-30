package pl.com.agora.makler.app.httpclient


import scala.util.{Failure, Success}
import akka.actor.ActorSystem
import akka.event.Logging

object Main extends App
  with RequestLevelApiDemo {

  // we always need an ActorSystem to host our application in
  implicit val system = ActorSystem("simple-example")
  import system.dispatcher // execution context for future transformations below
  val log = Logging(system, getClass)

  // the spray-can client-side API has three levels (from lowest to highest):
  // 1. the connection-level API
  // 2. the host-level API
  // 3. the request-level API
  //
  // this example demonstrates all three APIs by retrieving the server-version
  // of http://spray.io in three different ways
// /product/v1/pictures/RYSUNKI-KOZA.servlet?page=1&pageSize=5&limit=3&pictureFormat=K&apikey=fc3d4db6edee42cca2d04c3005e118ed
  val host = "api.gazeta.pl"

  val result = for {
    result3 <- demoRequestLevelApi(host)
  } yield Set( result3)

  result onComplete {
    case Success(res) => log.info("{} is running {}", host, res mkString ", ")
    case Failure(error) => log.warning("Error: {}", error)
  }
  result onComplete { _ => system.shutdown() }
}