package controllers

import java.util.concurrent.TimeUnit

import akka.actor.Actor
import org.joda.time.LocalTime
import play.api.libs.concurrent.Akka
import play.api.libs.iteratee.{Concurrent, Enumerator, Iteratee}
import play.api.mvc._

import scala.concurrent.duration.Duration


object Application extends Controller {
  import play.api.Play.current
  import scala.concurrent.ExecutionContext.Implicits.global
  val (out, channel) = Concurrent.broadcast[String]


  Akka.system.scheduler.schedule(
    Duration.Zero,
    Duration.create(5, TimeUnit.SECONDS),
    new Runnable {
      override def run(): Unit =    channel push(new LocalTime().toString("yyyy-MM-dd HH:mm:ss"))

    }
  );
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def timeSocket = WebSocket.using[String] { request =>
    import play.api.libs.concurrent.Execution.Implicits.defaultContext

    // Concurrent.broadcast returns (Enumerator, Concurrent.Channel)

    // log the message to stdout and send response back to client
    val in = Iteratee.foreach[String] {
      msg =>
    }
    (in,out)
  }
  def echoSocket = WebSocket.using[String] { request =>
    import play.api.libs.concurrent.Execution.Implicits.defaultContext

    // Concurrent.broadcast returns (Enumerator, Concurrent.Channel)

    // log the message to stdout and send response back to client
    val in = Iteratee.foreach[String] {
      msg => println(msg)
        // the Enumerator returned by Concurrent.broadcast subscribes to the channel and will
        // receive the pushed messages
        channel push(msg)
    }
    (in,out)
  }

}