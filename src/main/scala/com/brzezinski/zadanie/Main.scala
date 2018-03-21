package com.brzezinski.zadanie {
  
  object Main {

    import java.util.concurrent.{ScheduledThreadPoolExecutor, TimeUnit}
    import TimeUnit._
    import java.io.File

    import Conf._

    def main(args: Array[String]): Unit = {

      val create = TaskMapper.read _ andThen ImageDownloader.download andThen MD5Hasher.computeCheckSumsForAllFilesInDirectory

      val checkSums = create(JSON_FILENAME)
      val monitor = new Monitor(checkSums)

      val executor = new ScheduledThreadPoolExecutor(1)
      executor.scheduleAtFixedRate(monitor, 0, CHECK_RATE_IN_SECONDS, SECONDS)
    }

    object Conf {
      val JSON_FILENAME: String = "/home/przemek/Desktop/jsonik.txt"
      val CHECK_RATE_IN_SECONDS: Int = 25
    }

    case class Task(downloadDir: String, files: Map[String, String])

    trait JsonMapper[T] {
      def read(filename: String): T
    }

    object TaskMapper extends JsonMapper[Task] {

      import org.json4s._
      import org.json4s.jackson.JsonMethods._

      import scala.io.Source

      implicit val formats: DefaultFormats.type = DefaultFormats

      def read(filename: String): Task = {
        val source = Source.fromFile(filename)
        val json = parse(source.mkString)
        json.extract[Task]
      }
    }

    object ImageDownloader {

      import java.io.File
      import java.net.URL

      import sys.process._

      def download(task: Task): String = {
        def downloadImage(imageName: String, imageUrl: String) = {
          new URL(imageUrl) #> new File(imageName) !!
        }

        val dirName = task.downloadDir
        val dir = new File(dirName)
        if (!dir.exists())
          dir.mkdir()
        for ((k, v) <- task.files)
          downloadImage(dirName + k, v)
        dirName
      }
    }

    object MD5Hasher {

      import java.io.{File, FileInputStream}
      import java.security.{DigestInputStream, MessageDigest}

      def computeHash(file: File): String = {
        val buffer = new Array[Byte](8192)
        val md5 = MessageDigest.getInstance("MD5")

        val dis = new DigestInputStream(new FileInputStream(file), md5)
        try {
          while (dis.read(buffer) != -1) {}
        } finally {
          dis.close()
        }

        md5.digest.map("%02x".format(_)).mkString
      }

      def computeCheckSumsForAllFilesInDirectory(directory: String): Map[File, String] = {
        new File(directory).listFiles().filter(_.isFile).map(f => (f, computeHash(f)))(collection.breakOut)
      }
    }


    class Monitor(checkSums: Map[File, String]) extends Runnable {
      override def run(): Unit = {
        for ((k, v) <- checkSums) {
          val newHash = MD5Hasher.computeHash(k)
          if (!newHash.equals(v))
            throw new RuntimeException("CHECKSUMS HAS CHANGED")
        }
      }
    }

  }

}