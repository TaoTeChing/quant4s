/**
  *
  */
package org.quant4s.persistence

import scala.slick.driver.H2Driver.simple._
/**
  *
  */
trait BaseDao[T] {
// session: Session = null

  def getById(id: Int) : Option[T]

  def update(id: Int, entity: T): Unit

  def insert(entity: T): T

  def delete(id: Int): Unit

  def list(): List[T]
}
