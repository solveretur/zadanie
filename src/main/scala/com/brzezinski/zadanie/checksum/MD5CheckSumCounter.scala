package com.brzezinski.zadanie.checksum

import java.io.{File, FileInputStream}
import java.security.{DigestInputStream, MessageDigest}

object MD5CheckSumCounter {

  def computeMD5CheckSumForFile(file: File): String = {
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

  def computeMD5CheckSumForAllFilesInDirectory(directory: String): Map[File, String] = {
    new File(directory).listFiles().filter(_.isFile).map(f => (f, computeMD5CheckSumForFile(f)))(collection.breakOut)
  }
}
