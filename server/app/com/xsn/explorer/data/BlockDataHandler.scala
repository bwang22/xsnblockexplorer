package com.xsn.explorer.data

import javax.inject.Inject

import com.xsn.explorer.models._
import com.xsn.explorer.models.rpc.Block
import org.mongodb.scala.MongoDatabase
import org.mongodb.scala.bson.{BsonArray, BsonInt32, BsonInt64, BsonString}
import org.mongodb.scala.bson.collection.immutable.Document
import org.mongodb.scala.model.Filters._

import scala.concurrent.{ExecutionContext, Future}

trait BlockDataHandler {

}

class BlockMongoDataHandler @Inject() (
    database: MongoDatabase,
    blockMapper: MongoBlockMapper)(
    implicit ec: ExecutionContext)
    extends BlockDataHandler {

  private val collection = database.getCollection("blocks")

  def add(block: Block): Future[Unit] = {
    val document = blockMapper.toDocument(block)
    collection.insertOne(document).toFuture().map(_ => ())
  }

  def get(blockhash: Blockhash): Future[Block] = {
    val filter = equal("_id", blockhash.string)
    ???
    //collection.find(filter).first().toFuture().map()
  }
}

class MongoBlockMapper {

  import MongoBlockMapper.Keys

  def fromDocument(document: Document): Option[Block] = {
    for {
      blockhash <- document.get[BsonString](Keys.Id).map(_.getValue).flatMap(Blockhash.from)
      merkleRoot <- document.get[BsonString](Keys.MerkleRoot).map(_.getValue).flatMap(Blockhash.from)
      chainwork <- document.get[BsonString](Keys.Chainwork).map(_.getValue)
      size <- document.get[BsonInt32](Keys.Size).map(_.getValue).map(Size.apply)
      height <- document.get[BsonInt32](Keys.Height).map(_.getValue).map(Height.apply)
      version <- document.get[BsonInt32](Keys.Version).map(_.getValue)
      time <- document.get[BsonInt64](Keys.Time).map(_.getValue)
      medianTime <- document.get[BsonInt64](Keys.MedianTime).map(_.getValue)
      nonce <- document.get[BsonInt32](Keys.Nonce).map(_.getValue)
      bits <- document.get[BsonString](Keys.Bits).map(_.getValue)
      difficulty <- document.get[BigDecimal](Keys.Difficulty)
      transactions <- document.get[BsonArray](Keys.Transactions).map(_.).getOrElse(List.empty).map(_.getValue).flatMap(TransactionId.from)
    } yield ???
//      hash = blockhash,
//      merkleRoot = merkleRoot,
//      chainwork = chainwork,
//      size = size,
//      height = height,
//      version = version,
//      time = time,
//      medianTime = medianTime,
//      nonce = nonce,
//      bits = bits,
//      difficulty = difficulty,
//      transactions = transactions,
//      nextBlockhash = document.get[String](Keys.NextBlockhash).map(Blockhash.from),
//      previousBlockhash = document.get[String](Keys.PreviousBlockhash).map(Blockhash.from),
//      tposContract = document.get[String](Keys.TposContract).map(TransactionId.from),
//      confirmations = Confirmations(0)
//    )
  }
  def toDocument(block: Block): Document = {
    // confirmations is a dynamic value, it doesn't make sense to store it
    val document = Document(
      Keys.Id -> block.hash.string,
      Keys.MerkleRoot -> block.merkleRoot.string,
      Keys.Chainwork -> block.chainwork,
      Keys.Size -> block.size.int,
      Keys.Height -> block.height.int,
      Keys.Version -> block.version,
      Keys.Time -> block.time,
      Keys.MedianTime -> block.medianTime,
      Keys.Nonce -> block.nonce,
      Keys.Bits -> block.bits,
      Keys.Difficulty -> block.difficulty,
      Keys.Transactions -> block.transactions.map(_.string)
    )

    val optionals = List(
      block.previousBlockhash.map(Keys.PreviousBlockhash -> _.string),
      block.nextBlockhash.map(Keys.NextBlockhash -> _.string),
      block.tposContract.map(Keys.TposContract -> _.string)).flatten

    optionals.foldLeft(document) { case (result, (key, value)) =>
      result + (key -> value)
    }
  }
}

object MongoBlockMapper {
  object Keys {
    val Id = "_id"
    val MerkleRoot = "merkleRoot"
    val Chainwork = "chainwork"
    val Size = "size"
    val Height = "height"
    val Version = "version"
    val Time = "time"
    val MedianTime = "medianTime"
    val Nonce = "nonce"
    val Bits = "bits"
    val Difficulty = "difficulty"
    val Transactions = "transactions"
    val PreviousBlockhash = "previousBlockhash"
    val NextBlockhash = "nextBlockhash"
    val TposContract = "tposContract"
  }
}