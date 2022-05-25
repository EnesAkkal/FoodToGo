package com.enesakkal.foodToGo.data.network

import com.enesakkal.foodToGo.data.LocalDataSource
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class Repository @Inject constructor(
    remoteDataSource: RemoteDataSource,
    localDataSource: LocalDataSource
) {

    val remote  = remoteDataSource
    val local = localDataSource
}