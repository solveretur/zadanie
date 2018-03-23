package com.brzezinski.zadanie.downloadTask

import java.io.File

import com.brzezinski.zadanie.downloadtask.DownloadTask
import com.brzezinski.zadanie.downloadtask.DownloadTaskService._
import org.apache.commons.io.FileUtils
import org.junit.Assert._
import org.junit.Test
import org.scalatest.junit.AssertionsForJUnit

class DownloadTaskServiceTest extends AssertionsForJUnit {

  @Test def createDownloadTaskFromFileShouldCorrectParseValidJsonFromFile(): Unit = {
    //given
    val testJsonFilename = "./src/test/resources/testJson.json"
    val expectedDownloadDir = "path"
    val expectedFiles = Map("nazwa" -> "url", "nazwa2" -> "url2")
    //when
    val task = createDownloadTaskFromFile(testJsonFilename)
    //then
    assertEquals(expectedDownloadDir, task.downloadDir)
    assertEquals(expectedFiles, task.files)
  }

  @Test def downloadFilesShouldDownloadFilesAndReturnCorrectDirectory(): Unit = {
    //given
    val fileToDownloadPath = "./src/test/resources/testImage.jpg"
    val fileToDownload = new File(fileToDownloadPath)
    val fileToDownloadURLPath = "file://" + fileToDownload.getAbsolutePath

    val expectedDownloadDir = "./src/test/resources/"
    val expectedDownloadFileName = "dtest1.jpg"

    val testFiles = Map(expectedDownloadFileName -> fileToDownloadURLPath)

    val testDownloadTask = DownloadTask(expectedDownloadDir, testFiles)
    //when
    val resultDir: String = downloadFiles(testDownloadTask)
    //then
    assertEquals(expectedDownloadDir, resultDir)
    val downloadedFile = new File(expectedDownloadDir + expectedDownloadFileName)
    assertEquals(true, FileUtils.contentEquals(fileToDownload, downloadedFile))
    downloadedFile.delete()
  }

}
