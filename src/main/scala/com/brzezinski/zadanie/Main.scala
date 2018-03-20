package com.brzezinski.zadanie {

  object Main {

    def main(args: Array[String]): Unit = {
      import org.json4s._
      import org.json4s.jackson.JsonMethods._
    /*  implicit val formats = DefaultFormats
      val rawJson = "{'downloadDir': 'path', files: {'nazwa': 'url', 'nazwa2': 'url2'}}"


      val json = parse(rawJson)
      val t1 = json.extract[Task]
      println(t1.downloadDir)*/

      println("Hello");
    }


//    case class Task(downloadDir: String, files: Map[String, String])

  }

}