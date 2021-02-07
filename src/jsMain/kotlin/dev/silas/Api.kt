package dev.silas

import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.browser.window

val endpoint = window.location.origin // only needed until https://github.com/ktorio/ktor/issues/1695 is resolved

val jsonClient = HttpClient {
    install(JsonFeature) { serializer = KotlinxSerializer() }
}

suspend fun getMessages(): List<Message> {
    return jsonClient.get(endpoint + Message.path)
}

suspend fun addMessage(shoppingListItem: Message) {
    jsonClient.post<Unit>(endpoint + Message.path) {
        contentType(ContentType.Application.Json)
        body = shoppingListItem
    }
}

suspend fun deleteMessage(shoppingListItem: Message) {
    jsonClient.delete<Unit>(endpoint + Message.path + "/${shoppingListItem.id}")
}