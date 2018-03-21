package com.brzezinski.zadanie {

  import java.util.concurrent.ScheduledThreadPoolExecutor
  import java.util.concurrent.TimeUnit._

  import com.brzezinski.Configuration._
  import com.brzezinski.zadanie.checksum.FilesMD5CheckSumChangeMonitor
  import com.brzezinski.zadanie.checksum.MD5CheckSumCounter._
  import com.brzezinski.zadanie.downloadtask.DownloadTaskService._

  object Main {

    def main(args: Array[String]): Unit = {

      val createFilesMD5CheckSumMap = createDownloadTaskFromFile _ andThen downloadFiles andThen computeMD5CheckSumForAllFilesInDirectory

      val filesCheckSum = createFilesMD5CheckSumMap(JSON_FILENAME)
      val filesCheckSumChangeMonitor = new FilesMD5CheckSumChangeMonitor(filesCheckSum)

      val executor = new ScheduledThreadPoolExecutor(1)
      executor.scheduleAtFixedRate(filesCheckSumChangeMonitor, 0, CHECK_RATE_IN_SECONDS, SECONDS)
    }

  }

}