/**
  *
  */
package quanter.rest



/**
  * 回测报告测试
  */
class BackTestServiceSpec extends RoutingSpec  with StrategyService {
  implicit def actorRefFactory = system
  implicit def _log = null //akka.event.Logging(system, this)

  "获取回测报告是否正常" - {
    "策略测试 " in {
      // 创建一个策略
      // 执行策略
      //
      cancel()

    }

    "策略测试报告获取" in {
      cancel()
    }
  }

}
