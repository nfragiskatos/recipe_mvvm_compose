package com.nfragiskatos.recipe_mvvm_compose.api

import com.nfragiskatos.recipe_mvvm_compose.data.api.RecipeAPIService
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.common.truth.Truth.assertThat
import com.nfragiskatos.recipe_mvvm_compose.BuildConfig
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source

class RecipeAPIServiceTest() {

    private lateinit var service: RecipeAPIService
    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder().baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RecipeAPIService::class.java)
    }

    private fun enqueueMockResponse(fileName: String) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)
    }

    @Test
    fun getSearchedRecipes_sentRequest_receivedExpected() {
        runBlocking {

            enqueueMockResponse("reciperesponse.json")

            val body = service.getSearchedRecipes(
                1,
                "chicken"
            ).body()
            val request = server.takeRequest()
            val authHeader = request.getHeader("Authorization")


            assertThat(body).isNotNull()
            assertThat(request.path).isEqualTo("/api/recipe/search?page=1&query=chicken")
            assertThat(authHeader).isEqualTo("Token 9c8b06d329136da358c2d00e76946b0111ce2c48")
        }
    }

    @Test
    fun getSearchedRecipes_receivedResponse_correctPageSize() {
        runBlocking {
            enqueueMockResponse("reciperesponse.json")

            val body = service.getSearchedRecipes(
                1,
                "chicken"
            ).body()

            assertThat(body).isNotNull()
            assertThat(body!!.results.size).isEqualTo(30)
        }
    }
}