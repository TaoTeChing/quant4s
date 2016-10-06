/**
  *
  */
package quanter.brokerages.simulate

import quanter.brokerages.Brokerage
import quanter.rest.Trader


/**
  *
  */
class SimulateBrokerage(pname: String) extends Brokerage("模拟交易接口" + pname) {
  def this() {
    this("")
  }
  override def disconnect: Unit = {
    // NOP
  }

  override def isConnected: Boolean = true

  override def connect: Unit = {
    // NOP
  }

  override def keep: Unit ={
    // NOP
  }

  override def buy(code: String, price: Double, quantity: Int): Unit = {
    // TODO: 保存到仿真数据库
  }

  override def sell(code: String, price: Double, quantity: Int): Unit = {
    // TODO: 保存到仿真数据库
  }

  override var accountInfo: Trader = _
}
