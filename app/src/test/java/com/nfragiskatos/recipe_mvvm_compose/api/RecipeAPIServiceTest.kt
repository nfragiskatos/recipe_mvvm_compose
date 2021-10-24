package com.nfragiskatos.recipe_mvvm_compose.api

import com.nfragiskatos.recipe_mvvm_compose.data.api.RecipeAPIService
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.common.truth.Truth.assertThat

class RecipeAPIServiceTest {

    private lateinit var service: RecipeAPIService

    @Before
    fun setUp() {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://food2fork.ca")
            .build()
        service = retrofit.create(RecipeAPIService::class.java)
    }

    @Test
    fun getSearchedRecipes_sentRequest_receivedExpected() {
        runBlocking {
            val response = service.getSearchedRecipes(
                1,
                "chicken"
            )

            assertThat(response.isSuccessful).isTrue()

            val body = response.body()

            assertThat(body).isNotNull()
        }
    }

    @Test
    fun getSearchedRecipes_receivedResponse_correctPageSize() {
        runBlocking {
            val body = service.getSearchedRecipes(
                1,
                "chicken"
            ).body()

            assertThat(body).isNotNull()
            assertThat(body!!.results.size).isEqualTo(30)
        }
    }
}