package com.de.signcloud.ui.createcourse

import android.util.Log
import androidx.lifecycle.*
import com.de.signcloud.Screen
import com.de.signcloud.bean.SchoolResponse
import com.de.signcloud.repository.remote.CourseRepository
import com.de.signcloud.ui.components.textfieldstate.GenerateNotNullState
import com.de.signcloud.ui.components.textfieldstate.GenerateState
import com.de.signcloud.utils.Event
import com.de.signcloud.utils.Result
import com.de.signcloud.utils.getOrNull
import java.util.*

class CreateCourseViewModel() : ViewModel() {

    private val _state = State()
    val state: State
        get() = _state

    private val _schoolsSuggestions = CourseRepository.getSchoolsSuggestions()
    val schoolsSuggestions = Transformations.map(_schoolsSuggestions) {
        _schoolsSuggestions.value?.getOrNull()?.schools ?: emptyList()
    }

//    private val _schoolsSearchResult = CourseRepository.

    private val _navigateTo = MutableLiveData<Event<Screen>>()
    val navigateTo: LiveData<Event<Screen>>
        get() = _navigateTo

    private val _gradeItems = MutableLiveData(generateGradeItems())
    val gradeItems: LiveData<List<String>>
        get() = _gradeItems

    private val _semesterItems = MutableLiveData(generateSemesterItems())
    val semesterItems: LiveData<List<String>>
        get() = _semesterItems

//    private val _createCourse

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


class State {
    private val _courseNameState = GenerateNotNullState()
    val courseNameState: GenerateNotNullState
        get() = _courseNameState

    private val _gradeSelectedState = GenerateNotNullState()
    val gradeSelectedState: GenerateNotNullState
        get() = _gradeSelectedState

    private val _semesterSelectedState = GenerateNotNullState()
    val semesterSelectedState: GenerateNotNullState
        get() = _semesterSelectedState

    private val _schoolSelectState = GenerateNotNullState()
    val schoolSelectState: GenerateNotNullState
        get() = _schoolSelectState

    private val _courseRequirementsState = GenerateState()
    val courseRequirementsState: GenerateState
        get() = _courseRequirementsState

    private val _classScheduleState = GenerateState()
    val classScheduleState: GenerateState
        get() = _classScheduleState

    private val _examArrangementState = GenerateState()
    val examArrangementState: GenerateState
        get() = _examArrangementState

    fun clearState() {
        _courseNameState.text = ""
        _gradeSelectedState.text = ""
        _semesterSelectedState.text = ""
        _schoolSelectState.text = ""
        _courseRequirementsState.text = ""
        _classScheduleState.text = ""
        _examArrangementState.text = ""
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