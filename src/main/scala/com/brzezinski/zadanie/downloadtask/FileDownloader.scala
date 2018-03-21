package com.brzezinski.zadanie.downloadtask

import java.io.File
import java.net.URL

import scala.sys.process._

private[downloadtask] object FileDownloader {

  def download(task: DownloadTask): String = {
    def downloadFile(filename: String, fileUrl: String) = {
      new URL(fileUrl) #> new File(filename) !!
    }

    val dirName = task.downloadDir
    val dir = new File(dirName)
    if (!dir.exists())
      dir.mkdir()
    for ((k, v) <- task.files)
      downloadFile(dirName + k, v)
    dirName
  }
}