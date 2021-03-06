package it.turingtest.spotify.scala.client.logging

import play.api.Logger
import play.api.libs.ws.{WSRequest, WSResponse}
import play.api.mvc.Result

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * https://www.playframework.com/documentation/2.5.x/ScalaLogging
  */
trait AccessLogging {

  // @see https://www.playframework.com/documentation/2.5.x/SettingsLogger
  val accessLogger = Logger("spotify-scala-client")

  def withLogger(request: WSRequest): Future[WSResponse] = {
    logResponse { logRequest(request).get }
  }

  def logRequest(request: WSRequest): WSRequest = {
    accessLogger.info(s"${request.method} - ${request.url}")
    request
  }

  def logResponse(call: Future[WSResponse]): Future[WSResponse] = {
    call map { response: WSResponse =>
      // accessLogger.debug(s"${response.status}, ${response.statusText}")
      accessLogger.debug(response.body)
      response
    }
  }

}
