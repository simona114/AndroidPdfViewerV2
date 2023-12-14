package com.github.barteksc.pdfviewer.annotation.core

import android.graphics.PointF
import com.github.barteksc.pdfviewer.annotation.core.shapes.Circle
import com.github.barteksc.pdfviewer.annotation.core.shapes.Rectangle
import com.github.barteksc.pdfviewer.annotation.core.shapes.convertCoordinatesFrom
import com.github.barteksc.pdfviewer.annotation.core.shapes.generateRectangleEdges

// todo : use enum
data class Annotation(val type: String, val points: List<PointF>) {
}

fun Annotation.toRectangleShape(pageHeight: Int): Rectangle {
    // rect's corners mapped to image space
    val mappedPoints = listOf(
        points[0].convertCoordinatesFrom(pageHeight),
        points[1].convertCoordinatesFrom(pageHeight),
        points[2].convertCoordinatesFrom(pageHeight),
        points[3].convertCoordinatesFrom(pageHeight)
    )

    // rectangle shape's edges
    val rectangleShapeEdges = mappedPoints.generateRectangleEdges()

    return Rectangle(points = mappedPoints, edges = rectangleShapeEdges)
}

fun Annotation.toCircleShape(pageHeight: Int): Circle {
    // circle's start and end points mapped to image space
    val mappedPoints = listOf(
        points[0].convertCoordinatesFrom(pageHeight),
        points[1].convertCoordinatesFrom(pageHeight),
    )

    return Circle(points = mappedPoints)
}