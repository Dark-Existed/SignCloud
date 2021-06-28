package com.de.signcloud

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import java.security.InvalidParameterException
import java.security.SecureRandom

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
    CreateCourseResult,
    SearchCourse,
    ScanCode,
    SearchCourseResult,
    ChangeRole,
    CourseOperation,
    CourseDetail,
    CreateCheckIn,
    CheckIn,
    CheckInList,
    CourseStudent,
    CheckInDetail,
    CheckInHistory
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
        Screen.CreateCourseResult -> {
            findNavController().navigate(R.id.create_course_result_fragment)
        }
        Screen.SearchCourse -> {
            findNavController().navigate(R.id.search_course_fragment)
        }
        Screen.ScanCode -> {
            findNavController().navigate(R.id.scan_code_fragment)
        }
        Screen.SearchCourseResult -> {
            findNavController().navigate(R.id.search_course_result_fragment)
        }
        Screen.ChangeRole -> {
            findNavController().navigate(R.id.change_role_fragment)
        }
        Screen.CourseOperation -> {
            findNavController().navigate(R.id.course_operation_fragment)
        }
        Screen.CourseDetail -> {
            findNavController().navigate(R.id.course_detail_fragment)
        }
        Screen.CreateCheckIn -> {
            findNavController().navigate(R.id.create_check_in_fragment)
        }
        Screen.CheckIn -> {
            findNavController().navigate(R.id.check_in_fragment)
        }
        Screen.CheckInList -> {
            findNavController().navigate(R.id.check_in_list_fragment)
        }
        Screen.CourseStudent -> {
            findNavController().navigate(R.id.course_student_fragment)
        }
        Screen.CheckInDetail -> {
            findNavController().navigate(R.id.check_in_detail_fragment)
        }
        Screen.CheckInHistory -> {
            findNavController().navigate(R.id.check_in_history_fragment)
        }
    }

}

fun Fragment.navigate(to: Screen, from: Screen, args: Bundle?) {
    if (to == from) {
        throw InvalidParameterException("Can't navigate to $to")
    }
    when (to) {
        Screen.Welcome -> {
            findNavController().navigate(R.id.welcome_fragment, args)
        }
        Screen.SignUp -> {
            findNavController().navigate(R.id.sign_up_fragment, args)
        }
        Screen.SignIn -> {
            findNavController().navigate(R.id.sign_in_fragment, args)
        }
        Screen.SignInWithGithub -> {
            findNavController().navigate(R.id.sign_in_with_github_fragment, args)
        }
        Screen.BindPhone -> {
            findNavController().navigate(R.id.bind_phone_fragment, args)
        }
        Screen.ResetPassword -> {
            findNavController().navigate(R.id.reset_password_fragment, args)
        }
        Screen.Home -> {
            findNavController().navigate(R.id.home_fragment, args)
        }
        Screen.CreateCourse -> {
            findNavController().navigate(R.id.create_course_fragment, args)
        }
        Screen.SelectSchool -> {
            findNavController().navigate(R.id.select_school_fragment, args)
        }
        Screen.CreateCourseResult -> {
            findNavController().navigate(R.id.create_course_result_fragment, args)
        }
        Screen.SearchCourse -> {
            findNavController().navigate(R.id.search_course_fragment, args)
        }
        Screen.ScanCode -> {
            findNavController().navigate(R.id.scan_code_fragment, args)
        }
        Screen.SearchCourseResult -> {
            findNavController().navigate(R.id.search_course_result_fragment, args)
        }
        Screen.ChangeRole -> {
            findNavController().navigate(R.id.change_role_fragment, args)
        }
        Screen.CourseOperation -> {
            findNavController().navigate(R.id.course_operation_fragment, args)
        }
        Screen.CourseDetail -> {
            findNavController().navigate(R.id.course_detail_fragment, args)
        }
        Screen.CreateCheckIn -> {
            findNavController().navigate(R.id.create_check_in_fragment, args)
        }
        Screen.CheckIn -> {
            findNavController().navigate(R.id.check_in_fragment, args)
        }
        Screen.CheckInList -> {
            findNavController().navigate(R.id.check_in_list_fragment, args)
        }
        Screen.CourseStudent -> {
            findNavController().navigate(R.id.course_student_fragment, args)
        }
        Screen.CheckInDetail -> {
            findNavController().navigate(R.id.check_in_detail_fragment, args)
        }
        Screen.CheckInHistory -> {
            findNavController().navigate(R.id.check_in_history_fragment, args)
        }
    }
}