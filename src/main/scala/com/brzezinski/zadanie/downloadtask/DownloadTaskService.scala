package com.brzezinski.zadanie.downloadtask

object DownloadTaskService {

  def createDownloadTaskFromFile(fileName: String): DownloadTask = {
    val taskJsonMapper = DownloadTaskJsonMapper
    taskJsonMapper.read(fileName)
  }

  def downloadFiles(downloadTask: DownloadTask): String = {
    FileDownloader.download(downloadTask)
  }

}
