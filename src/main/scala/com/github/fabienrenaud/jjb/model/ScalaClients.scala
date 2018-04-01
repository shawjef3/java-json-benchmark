package com.github.fabienrenaud.jjb.model

import com.github.fabienrenaud.jjb.model.Clients.{Client, EyeColor, Partner}
import com.github.fabienrenaud.jjb.provider.JsoninterScalaProvider
import com.github.plokhotnyuk.jsoniter_scala.core._
import com.github.plokhotnyuk.jsoniter_scala.macros.{CodecMakerConfig, JsonCodecMaker}
import java.io.{InputStream, OutputStream}
import java.time.{LocalDate, OffsetDateTime}
import java.util.UUID
import scala.collection.JavaConverters._

case class ScalaClient(
  _id: Long,
  index: Int,
  guid: UUID,
  isActive: Boolean,
  balance: BigDecimal,
  picture: String,
  age: Int,
  eyeColor: EyeColor,
  name: String,
  gender: String,
  company: String,
  emails: Array[String],
  phones: Array[Long],
  address: String,
  about: String,
  registered: LocalDate,
  latitude: Double,
  longitude: Double,
  tags: Seq[String],
  partners: Seq[ScalaPartner]
) {
  def toClient: Client = {
    val client = new Client()
    client._id = _id
    client.index = index
    client.guid = guid
    client.isActive = isActive
    client.balance = balance.underlying()
    client.picture = picture
    client.age = age
    client.eyeColor = eyeColor
    client.name = name
    client.gender = gender
    client.company = company
    client.emails = emails
    client.phones = phones
    client.address = address
    client.about = about
    client.registered = registered
    client.latitude = latitude
    client.longitude = longitude
    client.tags = tags.asJava
    client.partners = partners.map(_.toPartner).asJava
    client
  }
}

object ScalaClient {
  def valueOf(c: Client): ScalaClient = {
    ScalaClient(
      c._id,
      c.index,
      c.guid,
      c.isActive,
      BigDecimal(c.balance),
      c.picture,
      c.age,
      c.eyeColor,
      c.name,
      c.gender,
      c.company,
      c.emails,
      c.phones,
      c.address,
      c.about,
      c.registered,
      c.latitude,
      c.longitude,
      c.tags.asScala,
      c.partners.asScala.map(ScalaPartner.valueOf)
    )
  }
}

case class ScalaPartner(
  id: Long,
  name: String,
  since: OffsetDateTime
) {
  def toPartner: Partner = {
    val p = new Partner
    p.id = id
    p.name = name
    p.since = since
    p
  }
}

object ScalaPartner {
  def valueOf(p: Partner): ScalaPartner = {
    ScalaPartner(
      p.id,
      p.name,
      p.since
    )
  }
}

case class ScalaClients(
  clients: Seq[ScalaClient]
) {
  def toClients: Clients = {
    val c = new Clients()
    c.clients = clients.map(_.toClient).asJava
    c
  }
}

object ScalaClients extends JsoninterScalaProvider[Clients] {
  def valueOf(c: Clients): ScalaClients = {
    ScalaClients(
      clients = c.clients.asScala.map(ScalaClient.valueOf)
    )
  }

  implicit val jsoniterScalaCodec: JsonValueCodec[ScalaClients] =
    JsonCodecMaker.make[ScalaClients](CodecMakerConfig())

  def deserialize(i :InputStream): Clients = {
    readFromStream(i).toClients
  }

  def serialize(out: OutputStream, t: Clients): Unit = {
    writeToStream(valueOf(t), out)
  }
}
