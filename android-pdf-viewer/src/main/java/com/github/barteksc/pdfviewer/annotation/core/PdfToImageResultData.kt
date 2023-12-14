package com.github.barteksc.pdfviewer.annotation.core

import com.github.barteksc.pdfviewer.annotation.core.shapes.Shape
import java.io.File

data class PdfToImageResultData(val originalPdfFile: File,val imageFile: File, val pageHeight:Int, val shapes: List<Shape>)