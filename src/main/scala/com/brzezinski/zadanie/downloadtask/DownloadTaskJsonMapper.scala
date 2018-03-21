package com.brzezinski.zadanie.downloadtask

import org.json4s._
import org.json4s.jackson.JsonMethods._

import scala.io.Source

object DownloadTaskJsonMapper extends JsonMapper {

  implicit val formats: DefaultFormats.type = DefaultFormats

  def read(filename: String): DownloadTask = {
    val source = Source.fromFile(filename)
    val json = parse(source.mkString)
    json.extract[DownloadTask]
  }
}
