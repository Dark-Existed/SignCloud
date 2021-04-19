package com.de.signcloud

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import java.security.InvalidParameterException

enum class Screen {
    Welcome,
    SignIn,
    SignUp,
    ResetPassword,
    Home,
    CreateCourse
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
        Screen.ResetPassword -> {
            findNavController().navigate(R.id.reset_password_fragment)
        }
        Screen.Home -> {
            findNavController().navigate(R.id.home_fragment)
        }
        Screen.CreateCourse -> {
            findNavController().navigate(R.id.create_course_fragment)
        }
    }

}