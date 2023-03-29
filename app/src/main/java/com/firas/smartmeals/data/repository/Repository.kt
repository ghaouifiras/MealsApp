package com.firas.smartmeals.data.repository

import android.content.Context
import com.firas.smartmeals.data.local.DataBase
import com.firas.smartmeals.data.model.Category
import com.firas.smartmeals.data.remote.ApiService
import com.firas.smartmeals.others.Resource
import com.firas.smartmeals.others.Utils
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class Repository @Inject constructor(
    private val apiService: ApiService,
    @ApplicationContext private val context: Context,
    private val db: DataBase

) {


    fun getListMeals(word: String): Flow<Resource<List<Category>>> = flow {
        var newData = emptyList<Category>()

        emit(Resource.Loading())
        val dataFromLocal = db.dao.getCategories()
        emit(Resource.Loading(dataFromLocal))

            try {
                emit(Resource.Loading())
                val dataFromServer = apiService.getListMeals()

                coroutineScope {
                    val jobs = dataFromServer.categories.map { category ->
                        async {
                            category.path = Utils.dowloadImageFromUrl(
                                context,
                                category.strCategoryThumb,
                                category.idCategory
                            )
                            category.path?.let { db.dao.insertCategorie(category) }
                        }

                    }
                    jobs.awaitAll()
                }


            } catch (e: HttpException) {

                emit(
                    Resource.Error(
                        message = "Oops, something went wrong!",
                        data = dataFromLocal
                    )
                )

            } catch (e: IOException) {

                emit(
                    Resource.Error(
                        message = "Couldn't reach server, check your internet connection.",
                        data = dataFromLocal
                    )
                )

            }

        newData = if (word.isBlank()) {
            db.dao.getCategories().sortedBy { cat ->
                cat.strCategory
            }
        } else {
            db.dao.searchCategorie(word).sortedBy { cat ->
                cat.strCategory
            }
        }


        emit(Resource.Success(newData))


    }.flowOn(Dispatchers.IO)




}