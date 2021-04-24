package com.de.signcloud

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import java.security.InvalidParameterException

enum class Screen {
    Welcome,
    SignIn,
    SignInWithGithub,
    BindPhone,
    SignUp,
    ResetPassword,
    Home,
    CreateCourse,
    SelectSchool,
}

fun Fragment.navigate(to: Screen, from: Screen) {
    if (to == from) {
        throw InvalidParameterException("Can't navigate to $to")
    }

    when (to) {
        Screen.Welcome -> {
            findNavController().navigate(R.id.welcome_fragment)
        }
        Screen.SignUp -> {
            findNavController().navigate(R.id.sign_up_fragment)
        }
        Screen.SignIn -> {
            findNavController().navigate(R.id.sign_in_fragment)
        }
        Screen.SignInWithGithub -> {
            findNavController().navigate(R.id.sign_in_with_github_fragment)
        }
        Screen.BindPhone -> {
            findNavController().navigate(R.id.bind_phone_fragment)
        }
        Screen.ResetPassword -> {
            findNavController().navigate(R.id.reset_password_fragment)
        }
        Screen.Home -> {
            findNavController().navigate(R.id.home_fragment)
        }
        Screen.CreateCourse -> {
            findNavController().navigate(R.id.create_course_fragment)
        }
        Screen.SelectSchool -> {
            findNavController().navigate(R.id.select_school_fragment)
        }
    }

}

fun Fragment.navigateWithArgs(to: Screen, from: Screen, args: Bundle) {
    if (to == from) {
        throw InvalidParameterException("Can't navigate to $to")
    }
    when (to) {
        Screen.SignIn -> {
            findNavController().navigate(R.id.sign_in_fragment, args)
        }
        Screen.SignUp -> {
            findNavController().navigate(R.id.sign_up_fragment, args)
        }
        Screen.ResetPassword -> {
            findNavController().navigate(R.id.reset_password_fragment, args)
        }
        Screen.BindPhone -> {
            findNavController().navigate(R.id.bind_phone_fragment, args)
        }
    }
}