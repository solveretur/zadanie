package com.brzezinski.zadanie.checksum

import java.io.File

class FilesMD5CheckSumChangeMonitor(filesCheckSum: Map[File, String]) extends Runnable {
  override def run(): Unit = {
    for ((file, checkSum) <- filesCheckSum) {
      val currentFileMD5CheckSum = MD5CheckSumCounter.computeMD5CheckSumForFile(file)
      if (!currentFileMD5CheckSum.equals(checkSum))
        throw new RuntimeException("CHECKSUMS HAS CHANGED")
    }
  }
}
