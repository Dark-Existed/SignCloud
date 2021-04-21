package com.de.signcloud.ui.createcourse

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.de.signcloud.Screen
import com.de.signcloud.utils.Event
import java.util.*

class CreateCourseViewModel() : ViewModel() {

    private val _navigateTo = MutableLiveData<Event<Screen>>()
    val navigateTo: LiveData<Event<Screen>>
        get() = _navigateTo

    private val _gradeItems = MutableLiveData(generateGradeItems())
    val gradeItems: LiveData<List<String>>
        get() = _gradeItems

    private val _semesterItems = MutableLiveData(generateSemesterItems())
    val semesterItems: LiveData<List<String>>
        get() = _semesterItems


    fun navigateToSelectSchool() {
        _navigateTo.value = Event(Screen.SelectSchool)
    }

    private fun generateGradeItems(itemSize: Int = 5): List<String> {
        val calendar: Calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val generateYearSequence = generateSequence(year) { (it - 1) }.map { it.toString() }
        return generateYearSequence.take(itemSize).toList()
    }

    private fun generateSemesterItems(itemSize: Int = 3): List<String> {
        val calendar: Calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val beginYears =
            generateSequence(year - 1) { it + 1 }.map { it.toString() }.take(itemSize).toList()
        val endYears =
            generateSequence(year) { it + 1 }.map { it.toString() }.take(itemSize).toList()
        return beginYears.zip(endYears) { begin, end ->
            "$begin-$end"
        }
    }

}


@Suppress("UNCHECKED_CAST")
class CreateCourseViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateCourseViewModel::class.java)) {
            return CreateCourseViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}