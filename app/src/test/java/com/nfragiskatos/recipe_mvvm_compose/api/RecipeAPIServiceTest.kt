package com.nfragiskatos.recipe_mvvm_compose.api

import com.nfragiskatos.recipe_mvvm_compose.data.api.RecipeAPIService
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
    fun getSearchedHeadlines_sentRequest_receivedExpected() {
        runBlocking {
            val body = service.getSearchedRecipes(
                1,
                "chicken"
            )
                .body()

            assert(body != null)
        }
    }
}