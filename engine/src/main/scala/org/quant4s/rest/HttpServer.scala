package org.quant4s.rest

import akka.actor.{Actor, ActorLogging, Props}
import spray.http.HttpHeaders.RawHeader
import spray.routing.RejectionHandler.Default
import spray.http.HttpHeaders.{`Access-Control-Allow-Headers`, `Access-Control-Allow-Methods`, `Access-Control-Allow-Origin`, `Access-Control-Max-Age`}
import spray.routing.directives.RespondWithDirectives.respondWithHeaders
import spray.routing.directives.MethodDirectives.options
import spray.routing.directives.RouteDirectives.complete
import spray.http.HttpMethods.{DELETE, GET, OPTIONS, POST, PUT}
import spray.http.{AllOrigins, StatusCodes}

/**
  *
  */
class HttpServer extends Actor with StrategyService with TradeAccountService with DataService with PickerService with ActorLogging {
  def actorRefFactory = context
  def systemRef = context.system
  implicit def executionContext = actorRefFactory.dispatcher
  private val allowOriginHeader = `Access-Control-Allow-Origin`(AllOrigins)
  private val optionsCorsHeaders = List(
    `Access-Control-Allow-Headers`("Origin, X-Requested-With, Content-Type, Accept, Accept-Encoding, Accept-Language, Host, Referer, User-Agent, Authorization"),
    `Access-Control-Max-Age`(1728000)) //20 days


  def receive = runRoute( respondWithHeaders(`Access-Control-Allow-Methods`(OPTIONS, GET, POST, DELETE, PUT) ::  allowOriginHeader :: optionsCorsHeaders){
    optionsRoute ~ strategyServiceRoute ~ tradeAccountServiceRoute ~ dataServiceRoute ~ pickerServiceRoute
  }) orElse respTradeAccountReceive orElse respStrategyReceive

  val optionsRoute = {
    options {
      complete(StatusCodes.OK)
    }
  }
}

object HttpServer {
  def props: Props = {
    Props(classOf[HttpServer])
  }

  val path = "restServer"
}
