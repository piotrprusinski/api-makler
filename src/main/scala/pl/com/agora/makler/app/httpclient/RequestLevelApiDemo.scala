package pl.com.agora.makler.app.httpclient

import scala.concurrent.Future
import scala.concurrent.duration._
import akka.actor.ActorSystem
import akka.io.IO
import akka.pattern.ask
import akka.util.Timeout
import spray.http._
import spray.can.Http
import HttpMethods._

trait RequestLevelApiDemo {
  private implicit val timeout: Timeout = 5.seconds

  // The request-level API is the highest-level way to access the spray-can client-side infrastructure.
  // All you have to do is to send an HttpRequest instance to `IO(Http)` and wait for the response.
  // The spray-can HTTP infrastructure looks at the URI (or the Host header if the URI is not absolute)
  // to figure out which host to send the request to. It then sets up a HostConnector for that host
  // (if it doesn't exist yet) and forwards it the request.
  def demoRequestLevelApi(host: String)(implicit system: ActorSystem): Future[String] = {
    import system.dispatcher // execution context for future transformation below
    for {
      
      
      response <- IO(Http).ask(HttpRequest(GET, Uri(s"http://$host/product/v1/pictures/RYSUNKI-KOZA.servlet?apikey=fc3d4db6edee42cca2d04c3005e118ed"))).mapTo[HttpResponse]
    } yield {
      system.log.info("Request-Level API: received {} response with {} bytes",
        response.status, response.entity.data.length)
        response.entity.asString
        //response.header[HttpHeaders.Server]
      //response.header[HttpHeaders.Server].get.products.head
    }
  }
}