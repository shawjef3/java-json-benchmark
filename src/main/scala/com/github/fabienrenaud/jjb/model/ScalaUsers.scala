package com.github.fabienrenaud.jjb.model

import com.github.fabienrenaud.jjb.model.Users.{Friend, User}
import com.github.fabienrenaud.jjb.provider.JsoninterScalaProvider
import com.github.plokhotnyuk.jsoniter_scala.core._
import com.github.plokhotnyuk.jsoniter_scala.macros._
import java.io.{InputStream, OutputStream}
import scala.collection.JavaConverters._

case class ScalaUsers(
  users: Seq[ScalaUser]
) {
  def toUsers: Users = {
    val u = new Users()
    u.users = users.map(_.toUser).asJava
    u
  }
}

object ScalaUsers extends JsoninterScalaProvider[Users] {
  def valueOf(u: Users): ScalaUsers = {
    ScalaUsers(u.users.asScala.map(ScalaUser.valueOf))
  }

  implicit val jsoniterScalaCodec: JsonValueCodec[ScalaUsers] =
    JsonCodecMaker.make[ScalaUsers](CodecMakerConfig())

  override def deserialize(i: InputStream): Users = {
    readFromStream(i).toUsers
  }

  override def serialize(
    out: OutputStream,
    t: Users
  ): Unit = {
    writeToStream(valueOf(t), out)
  }
}


case class ScalaUser(
  _id: String,
  index: Int,
  guid: String,
  isActive: Boolean,
  balance: String,
  picture: String,
  age: Int,
  eyeColor: String,
  name: String,
  gender: String,
  company: String,
  email: String,
  phone: String,
  address: String,
  about: String,
  registered: String,
  latitude: Double,
  longitude: Double,
  tags: Seq[String],
  friends: Seq[ScalaFriend],
  greeting: String,
  favoriteFruit: String
) {
  def toUser: User = {
    val u = new User()
    u._id = _id
    u.index = index
    u.guid = guid
    u.isActive = isActive
    u.balance = balance
    u.picture = picture
    u.age = age
    u.eyeColor = eyeColor
    u.name = name
    u.gender = gender
    u.company = company
    u.email = email
    u.phone = phone
    u.about = about
    u.address = address
    u.about = about
    u.registered = registered
    u.latitude = latitude
    u.longitude = longitude
    u.tags = tags.asJava
    u.friends = friends.map(_.toFriend).asJava
    u.greeting = greeting
    u.favoriteFruit = favoriteFruit
    u
  }
}

object ScalaUser {
  def valueOf(u: User): ScalaUser = {
    ScalaUser(
      u._id,
      u.index,
      u.guid,
      u.isActive,
      u.balance,
      u.picture,
      u.age,
      u.eyeColor,
      u.name,
      u.gender,
      u.company,
      u.email,
      u.phone,
      u.address,
      u.about,
      u.registered,
      u.latitude,
      u.longitude,
      u.tags.asScala,
      u.friends.asScala.map(ScalaFriend.valueOf),
      u.greeting,
      u.favoriteFruit
    )
  }
}

case class ScalaFriend(
  id: String,
  name: String
) {
  def toFriend: Friend = {
    val f = new Friend()
    f.id = id
    f.name = name
    f
  }
}

object ScalaFriend {
  def valueOf(f: Friend): ScalaFriend = {
    ScalaFriend(f.id, f.name)
  }
}