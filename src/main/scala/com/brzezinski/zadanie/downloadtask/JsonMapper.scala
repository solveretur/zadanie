package com.brzezinski.zadanie.downloadtask

trait JsonMapper {
  def read(filename: String): DownloadTask
}
