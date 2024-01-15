package com.github.barteksc.pdfviewer.annotation.core.shapes

import android.graphics.PointF
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

data class Rectangle(
    override val type: String = ShapeType.RECTANGLE.name,
    override val points: List<PointF> = emptyList(),
    val edges: List<Edge> = emptyList(),
    val relations: Relations? = null,
) : Shape(type, points)

fun List<PointF>.generateRectangleEdges(): List<Edge> {
    val edgeTopHorizontal = Edge(this[0], this[1])
    val edgeRightVertical = Edge(this[1], this[2])
    val edgeBottomHorizontal = Edge(this[2], this[3])
    val edgeLeftVertical = Edge(this[3], this[0])
    return listOf(edgeTopHorizontal, edgeRightVertical, edgeBottomHorizontal, edgeLeftVertical)
}

data class Edge(val start: PointF, val end: PointF)

class RectangleTypeAdapter : JsonSerializer<Rectangle>, JsonDeserializer<Rectangle> {
    override fun serialize(
        src: Rectangle?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        val jsonObject = JsonObject()
        jsonObject.addProperty("type", src?.type)
        jsonObject.add(
            "points",
            context?.serialize(src?.points, object : TypeToken<List<PointF>>() {}.type)
        )
        jsonObject.add(
            "edges",
            context?.serialize(src?.edges, object : TypeToken<List<Edge>>() {}.type)
        )
        jsonObject.add(
            "relations",
            context?.serialize(src?.relations, object : TypeToken<Relations>() {}.type)
        )
        return jsonObject
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Rectangle {
        val jsonObject = json?.asJsonObject
        val type = jsonObject?.get("type")?.asString ?: ShapeType.RECTANGLE.name
        val corners = context?.deserialize<List<PointF>>(
            jsonObject?.get("points"),
            object : TypeToken<List<PointF>>() {}.type
        ) ?: emptyList()
        val edges = context?.deserialize<List<Edge>>(
            jsonObject?.get("edges"),
            object : TypeToken<List<Edge>>() {}.type
        ) ?: emptyList()
        val relations = context?.deserialize<Relations>(
            jsonObject?.get("relations"),
            object : TypeToken<Relations>() {}.type
        )
        return Rectangle(type, corners, edges, relations)
    }
}