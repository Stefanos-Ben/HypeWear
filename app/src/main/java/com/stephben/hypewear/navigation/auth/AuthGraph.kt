package com.stephben.hypewear.navigation.auth

import android.util.Log
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.stephben.hypewear.auth.presentation.brand_signup.BrandSignUpScreen
import com.stephben.hypewear.navigation.Route
import com.stephben.hypewear.auth.presentation.email_verification.EmailVerificationScreen
import com.stephben.hypewear.auth.presentation.forgot_password.ForgotPasswordScreen
import com.stephben.hypewear.auth.presentation.signin.SignInScreen
import com.stephben.hypewear.auth.presentation.signup.SignUpScreen


fun NavGraphBuilder.authGraph(
    navController: NavHostController,
) {
    navigation<Route.AuthGraph>(
        startDestination = Route.AuthGraph.SignIn
    ){
        composable<Route.AuthGraph.SignIn> {
            SignInScreen(
                onSignInSuccess = { destination ->
                    Log.i("NAVIGATION", "The destination is $destination")
                    navController.navigate(
                        if (destination == "default") Route.MainGraph.HomeScreen
                        else Route.BrandGraph.BrandHome
                    ) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                },
                onNavigateToSignUp = {
                    navController.navigate(Route.AuthGraph.SignUp) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                },
                onForgotPasswordClick = {
                    navController.navigate(Route.AuthGraph.ForgotPassword) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                },
                onEmailVerificationNeeded = { destination ->
                    navController.navigate(Route.AuthGraph.EmailVerification(destination)) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                },
                onNavigateToBrandSignUp = {
                    navController.navigate(Route.AuthGraph.BrandSignUp) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<Route.AuthGraph.SignUp> {
            SignUpScreen(
                onSignUpSuccess = {
                    navController.navigate(Route.AuthGraph.EmailVerification("default")) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                },
                onBackToSignIn = {
                    navController.popBackStack()
                }
            )
        }

        composable<Route.AuthGraph.BrandSignUp> {
            BrandSignUpScreen(
                onBackToSignIn = {navController.popBackStack()},
                onSignUpSuccess = {
                    navController.navigate(Route.AuthGraph.EmailVerification("brand")) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<Route.AuthGraph.ForgotPassword> {
            ForgotPasswordScreen(
                onPasswordResetSent = { navController.popBackStack() },
                onBackToSignIn = { navController.popBackStack() }
            )
        }

        composable<Route.AuthGraph.EmailVerification> { entry ->
            val args = entry.toRoute<Route.AuthGraph.EmailVerification>()
            EmailVerificationScreen(
                onContinue = {
                    navController.navigate(
                       if (args.userType == "default") Route.MainGraph else Route.BrandGraph
                    ) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                },
                onBackToSignIn = {
                    navController.navigate(Route.AuthGraph.SignIn) {
                        popUpTo(navController.graph.findStartDestination().id){
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}