package com.stephben.hypewear.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.stephben.hypewear.apparel.data.ApparelRepositoryImpl
import com.stephben.hypewear.apparel.domain.ApparelRepository
import com.stephben.hypewear.apparel.presentation.apparel_detail.ApparelDetailViewModel
import com.stephben.hypewear.apparel.presentation.apparel_home.ApparelListViewModel
import com.stephben.hypewear.apparel.presentation.tempadd.AddApparelViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module


val appModule = module {
    single<FirebaseFirestore> {
        Firebase.firestore
    }

    single<CoroutineDispatcher>(
        named("IoDispatcher")
    ) {
        Dispatchers.IO
    }


    single<ApparelRepository> {
        ApparelRepositoryImpl(
            hypeWearDb = get(),
            ioDispatcher = get(named("IoDispatcher"))
        )
    }

    viewModel {
        ApparelListViewModel(
            apparelRepository = get()
        )
    }

    viewModel {
        AddApparelViewModel(
            repository = get()
        )
    }

    viewModel {
        ApparelDetailViewModel(
            repository = get()
        )
    }
}