package com.xsn.explorer.services

import javax.inject.Inject

import com.alexitc.playsonify.core.FutureApplicationResult
import com.alexitc.playsonify.core.FutureOr.Implicits.{FutureOps, OrOps}
import com.xsn.explorer.models.base._
import com.xsn.explorer.models.fields.MasternodeField
import com.xsn.explorer.models.rpc.Masternode
import com.xsn.explorer.parsers.MasternodeOrderingParser
import com.xsn.explorer.services.validators.PaginatedQueryValidator

import scala.concurrent.ExecutionContext

class MasternodeService @Inject() (
    queryValidator: PaginatedQueryValidator,
    masternodeOrderingParser: MasternodeOrderingParser,
    xsnService: XSNService)(
    implicit ec: ExecutionContext) {

  def getMasternodes(paginatedQuery: PaginatedQuery, orderingQuery: OrderingQuery): FutureApplicationResult[PaginatedResult[Masternode]] = {
    val result = for {
      validatedQuery <- queryValidator.validate(paginatedQuery).toFutureOr
      ordering <- masternodeOrderingParser.from(orderingQuery).toFutureOr
      masternodes <- xsnService.getMasternodes().toFutureOr
    } yield build(masternodes, validatedQuery, ordering)

    result.toFuture
  }

  private def build(list: List[Masternode], query: PaginatedQuery, ordering: FieldOrdering[MasternodeField]) = {
    val partial = sort(list, ordering)
        .slice(query.offset.int, query.offset.int + query.limit.int)

    PaginatedResult(query.offset, query.limit, Count(list.size), partial)
  }

  private def sort(list: List[Masternode], ordering: FieldOrdering[MasternodeField]) = {
    val sorted = sortByField(list, ordering.field)
    applyOrderingCondition(sorted, ordering.orderingCondition)
  }

  private def sortByField(list: List[Masternode], field: MasternodeField) = field match {
    case MasternodeField.ActiveSeconds => list.sortBy(_.activeSeconds)
    case MasternodeField.IP => list.sortBy(_.ip)
    case MasternodeField.LastSeen => list.sortBy(_.lastSeen)
    case MasternodeField.Status => list.sortBy(_.status)
  }

  private def applyOrderingCondition[A](list: List[A], orderingCondition: OrderingCondition) = orderingCondition match {
    case OrderingCondition.AscendingOrder => list
    case OrderingCondition.DescendingOrder => list.reverse
  }
}
