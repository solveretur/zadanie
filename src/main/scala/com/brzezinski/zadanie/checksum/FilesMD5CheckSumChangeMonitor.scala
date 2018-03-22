package com.brzezinski.zadanie.checksum

import java.io.{File, FileNotFoundException}

import com.typesafe.scalalogging.LazyLogging

class FilesMD5CheckSumChangeMonitor(filesCheckSum: Map[File, String]) extends Runnable with LazyLogging {
  override def run(): Unit = {
    logger.info("Checking files MD5 checksum")
    for ((file, checkSum) <- filesCheckSum) {
      try {
        val currentFileMD5CheckSum = MD5CheckSumCounter.computeMD5CheckSumForFile(file)
        if (!checkSum.equals(currentFileMD5CheckSum))
          logger.info("CHECKSUM HAS CHANGED FOR FILE {}", file.getAbsolutePath)
      } catch {
        case e: FileNotFoundException => logger.info("FILE {} WAS DELETED", file.getAbsolutePath)
      }
    }
  }
}
