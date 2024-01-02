package com.github.barteksc.pdfviewer.annotation.core

import java.io.File

data class PdfToImageResultData(
    val pdfFile: File,
    val imageFile: File,
    val pageHeight: Int,
    val jsonShapes: String
)