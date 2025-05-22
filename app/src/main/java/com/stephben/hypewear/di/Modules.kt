package com.stephben.hypewear.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.stephben.hypewear.apparel.data.ApparelRepositoryImpl
import com.stephben.hypewear.apparel.domain.ApparelFormValidateUseCase
import com.stephben.hypewear.apparel.domain.ApparelRepository
import com.stephben.hypewear.apparel.presentation.apparel_detail.ApparelDetailViewModel
import com.stephben.hypewear.apparel.presentation.apparel_form.ApparelFormViewModel
import com.stephben.hypewear.apparel.presentation.home_screen.HomeScreenViewModel
import com.stephben.hypewear.apparel.presentation.tempadd.AddApparelViewModel
import com.stephben.hypewear.auth.data.AuthRepositoryImpl
import com.stephben.hypewear.auth.domain.AuthRepository
import com.stephben.hypewear.auth.presentation.brand_signup.BrandSignUpViewModel
import com.stephben.hypewear.brand.data.BrandRepositoryImpl
import com.stephben.hypewear.brand.domain.BrandRepository
import com.stephben.hypewear.user.data.UserRepositoryImpl
import com.stephben.hypewear.user.domain.UserRepository
import com.stephben.hypewear.auth.presentation.email_verification.EmailVerificationViewModel
import com.stephben.hypewear.auth.presentation.forgot_password.ForgotPasswordViewModel
import com.stephben.hypewear.user.presentation.profile.ProfileViewModel
import com.stephben.hypewear.auth.presentation.signin.SignInViewModel
import com.stephben.hypewear.auth.presentation.signup.SignUpViewModel
import com.stephben.hypewear.brand.presentation.brand_home.BrandHomeViewModel
import com.stephben.hypewear.brand.presentation.collection.CollectionViewModel
import com.stephben.hypewear.brand.presentation.profile.BrandProfileViewModel
import com.stephben.hypewear.core.presentation.splash_screen.SplashScreenViewModel
import com.stephben.hypewear.user.presentation.favorites.FavoritesViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module


val appModule = module {

    single<FirebaseFirestore> {
        Firebase.firestore
    }

    single<FirebaseAuth> {
        Firebase.auth
    }

    single<CoroutineDispatcher>(
        named("IoDispatcher")
    ) {
        Dispatchers.IO
    }

    single<ApparelFormValidateUseCase> {
        ApparelFormValidateUseCase()
    }

    single<ApparelRepository> {
        ApparelRepositoryImpl(
            hypeWearDb = get(),
            ioDispatcher = get(named("IoDispatcher"))
        )
    }

    single<BrandRepository> {
        BrandRepositoryImpl(
            hypeWearDb = get(),
            auth = get(),
            ioDispatcher = get(named("IoDispatcher"))
        )
    }

    single<UserRepository> {
        UserRepositoryImpl(
            auth = get(),
            firestore = get(),
            ioDispatcher = get(named("IoDispatcher"))
        )
    }

    single<AuthRepository> {
        AuthRepositoryImpl(
            auth = get(),
            firestore = get(),
            ioDispatcher = get(named("IoDispatcher"))
        )
    }

    viewModel {
        HomeScreenViewModel(
            apparelRepository = get(),
            userRepository = get()
        )
    }

    viewModel {
        AddApparelViewModel(
            apparelRepository = get(),
            brandRepository = get()
        )
    }

    viewModel {
        ApparelDetailViewModel(
            apparelRepository = get(),
            userRepository = get()
        )
    }

    viewModel {
      SignInViewModel(
          authRepository = get(),
          auth = get()
      )
    }

    viewModel {
        SignUpViewModel(
            authRepository = get()
        )
    }

    viewModel {
        BrandSignUpViewModel(
            authRepository = get()
        )
    }

    viewModel {
        ForgotPasswordViewModel(
            auth = get()
        )
    }

    viewModel {
        EmailVerificationViewModel(
            auth = get(),
            authRepository = get()
        )
    }

    viewModel {
        ProfileViewModel(
            authRepository = get(),
            userRepository = get()
        )
    }

    viewModel {
        FavoritesViewModel(
            userRepository = get(),
            apparelRepository = get(),
        )
    }

    viewModel {
        BrandHomeViewModel(
            brandRepository = get()
        )
    }

    viewModel {
        SplashScreenViewModel(
            auth = get(),
            userRepository = get()
        )
    }

    viewModel {
        ApparelFormViewModel(
            validate = get(),
            repository = get(),
            brandRepository = get()
        )
    }

    viewModel {
        BrandProfileViewModel(
            userRepository = get(),
            authRepository = get()
        )
    }

    viewModel {
        CollectionViewModel(
            apparelRepository = get(),
            brandRepository = get()
        )
    }
}