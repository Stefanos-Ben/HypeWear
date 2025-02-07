package com.stephben.hypewear.di

import com.google.firebase.firestore.FirebaseFirestore
import com.stephben.hypewear.apparel.data.ApparelRepositoryImpl
import com.stephben.hypewear.apparel.domain.ApparelRepository
import com.stephben.hypewear.apparel.presentation.apparel_list.ApparelListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single {
        FirebaseFirestore.getInstance()
    }

    single<ApparelRepository>{
        ApparelRepositoryImpl(
            firestore = get()
        )
    }

    viewModelOf(::ApparelListViewModel)
}