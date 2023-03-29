package com.firas.smartmeals.data.remote

import com.firas.smartmeals.data.model.Meal
import com.firas.smartmeals.data.model.Meals
import retrofit2.http.GET

interface ApiService {

    @GET("categories.php")
    suspend fun getListMeals(): Meals

}