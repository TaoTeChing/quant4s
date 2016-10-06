package quanter.rest

import java.util.HashMap

import akka.actor.{ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout
import com.typesafe.config.ConfigFactory
import org.json4s.jackson.JsonMethods._
import org.json4s.{DefaultFormats, Extraction, Formats}
import quanter.actors.trade._
import quanter.actors._
import quanter.actors.strategy.StrategiesManagerActor
import quanter.config.Settings
import spray.routing.HttpService

import scala.collection.mutable.ArrayBuffer
import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext}

/**
  *
  */
trait TradeAccountService extends HttpService{
  implicit def systemRef: ActorSystem
  val tradeAccountServiceRoute = {
    get {
      path("account" / "list") {
        complete {
          _getAllTraders()
        }
      }~
      path("config" / "brokerageChannelTypes") {
        complete {
          _listChannelTypes()
        }
      }
    } ~
    post {
      path("account") {
        requestInstance {
          request => {
            complete {
              _createTrader(request.entity.data.asString)
            }
          }
        }
      }
    } ~
    put {
      path("account" / IntNumber) {
        id => {
          requestInstance {
            request => {
              complete {
                _updateTrader(request.entity.data.asString)
              }
            }
          }
        }
      }~
      path("account" / "connect" / IntNumber) {
        id => {
          complete {
            _reconnect(id)
          }
        }
      }
    } ~
    delete {
      path("account" / IntNumber) {
        id => {
          complete {
             _deleteTrader(id)
          }
        }
      }
    }
  }

  val traderManager = actorRefFactory.actorSelection("/user/" + TradeRouteActor.path)
  private def _getAllTraders(): String = {
    implicit val timeout = Timeout(10 seconds)
    val future = traderManager ? new ListTraders()
    val result = Await.result(future, timeout.duration).asInstanceOf[Option[Array[Trader]]]
    val retTraders = RetTraderList(0, "success", result)
    implicit val formats: Formats = DefaultFormats
    val json = Extraction.decompose(retTraders)
    compact(render(json))
  }

  private def _listChannelTypes(): String = {
//     val config = ConfigFactory.load("application.conf")
    val setting = Settings(systemRef)

    val channelTypes = new ArrayBuffer[ChannelType]()
    for(i <- 0 until setting.channelTypes.size()) {
      val provider = setting.channelTypes.get(i).asInstanceOf[HashMap[String, String]]
      val name = provider.get("name")
      val title = provider.get("title")
      val desc = provider.get("desc")
      val driver = provider.get("driver")

      val channelType = new ChannelType(name, title, desc, driver)
      channelTypes += channelType
    }

    val retChannelTypes = new RetChannelTypes(0, "", channelTypes.toArray)
    implicit val formats: Formats = DefaultFormats
    val json = Extraction.decompose(retChannelTypes)
    compact(render(json))
  }

  private def _reconnect(id: Int): String = {
    val tradeAccountRef = actorRefFactory.actorSelection("/user/" + TradeRouteActor.path + "/" + id.toString)
    tradeAccountRef ! new Connect()
    """{"code": 0}"""
  }

  private def _createTrader(json: String): String = {
    // 将JSON转换为strategy，加入到strategy
    implicit val formats = DefaultFormats
    try {
      val jv = parse(json)
      val trader = jv.extract[Trader]

      traderManager ! NewTrader(trader)
      """{"code":0}"""
    }catch {
      case ex: Exception => """{"code":1, "message":"%s"}""".format(ex.getMessage)
    }
  }

  private def _updateTrader(json: String): String = {
    implicit val formats = DefaultFormats
    try {
      val jv = parse(json)
      val trader = jv.extract[Trader]

      traderManager ! new UpdateTrader(trader)
      """{"code":0}"""
    }catch {
      case ex: Exception => """{"code":1, "message":"%s"}""".format(ex.getMessage)
    }

  }

  private def _deleteTrader(id: Int): String = {
    try {
      //      strategiesManager.removeStrategy(id)
      traderManager ! DeleteTrader(id)
      """{"code":0, "message":"成功删除"}"""
    }catch {
      case ex: Exception => """{"code":1, "message":"%s"}""".format(ex.getMessage)
    }
  }
}
