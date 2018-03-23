package com.brzezinski.zadanie.checksum

import java.io.File

import com.brzezinski.zadanie.checksum.MD5CheckSumCounter._
import org.junit.Assert.assertEquals
import org.junit.Test
import org.scalatest.junit.AssertionsForJUnit

class MD5CheckSumCounterTest extends AssertionsForJUnit {

  @Test def computeMD5CheckSumForFileShouldComputeCorrectMD5Hash(): Unit = {
    val testFilename = "./src/test/resources/testImage.jpg"
    val expectedCheckSum = "CE1258F249A16E57F8BBE1F8EBBC3641"

    val computedCheckSum = computeMD5CheckSumForFile(new File(testFilename))

    assertEquals(true, expectedCheckSum.equalsIgnoreCase(computedCheckSum))
  }
}
