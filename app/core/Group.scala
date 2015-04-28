package core

import core.connector.MorphiaFactory
import models._
import org.mongodb.morphia.query.{Query, UpdateOperations}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import scala.collection.JavaConversions._

import scala.concurrent.Future

/**
 * Created by zephyre on 4/20/15.
 */
object Group {
  val ds = MorphiaFactory.getDatastore()
  val miscDs = MorphiaFactory.getDatastore("misc")

  /**
   * 通过id获得conversation信息
   */
  def createGroup(creator: Long, name: String, groupType: String, isPublic: Boolean): Future[Group] = {
    Future {
      val gId = populateUserId
      val c = models.Group.create(creator, gId, name, groupType, isPublic)
      ds.save[Group](c)
      c
    }
  }

  def populateUserId: Long = {
    val query: Query[Sequence] = miscDs.createQuery(classOf[Sequence])
    query.field("column").equal(Sequence.GROUPID)
    val ops: UpdateOperations[Sequence] = miscDs.createUpdateOperations(classOf[Sequence]).inc("count")
    val ret: Sequence = miscDs.findAndModify(query, ops)
    ret.count
  }

}
