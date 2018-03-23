package com.brzezinski.zadanie.downloadtask

import org.json4s._
import org.json4s.jackson.JsonMethods._

private[downloadtask] object DownloadTaskJsonMapper extends JsonMapper {

  implicit val formats: DefaultFormats.type = DefaultFormats

  override def parseRawJsonString(rawJson: String): DownloadTask = {
    val json = parse(rawJson)
    json.extract[DownloadTask]
  }

}
