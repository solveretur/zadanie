package com.brzezinski.zadanie.downloadtask

import scala.io.Source

trait JsonMapper {
  def parseRawJsonString(rawJson: String): DownloadTask

  def readFromFile(filename: String): DownloadTask = {
    val source = Source.fromFile(filename)
    parseRawJsonString(source.mkString)
  }
}
