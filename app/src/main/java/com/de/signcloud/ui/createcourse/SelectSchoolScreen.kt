package com.de.signcloud.ui.createcourse

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.de.signcloud.R
import com.de.signcloud.bean.SchoolResponse
import com.de.signcloud.ui.components.SignCloudDivider
import com.de.signcloud.ui.components.SignCloudSurface
import com.de.signcloud.ui.theme.SignCloudTheme
import com.google.accompanist.insets.statusBarsPadding

sealed class SelectSchoolEvent {

}

@Composable
fun SelectSchool(
    modifier: Modifier = Modifier,
    allSchools: List<SchoolResponse.Data.School> = emptyList(),
    state: SearchState = rememberSearchState(suggestions = allSchools),
    onItemClick: (String, String) -> Unit
) {
    SignCloudSurface(modifier = modifier.fillMaxSize()) {
        Column {
            Spacer(modifier = Modifier.statusBarsPadding())
            SearchBar(
                query = state.query,
                onQueryChange = { state.query = it },
                searchFocused = state.focused,
                onSearchFocusChange = { state.focused = it },
                onClearQuery = { state.query = TextFieldValue("") },
                searching = state.searching
            )
            SignCloudDivider()

            LaunchedEffect(state.query.text) {
                state.searching = true
                state.searchResults = allSchools.filter {
                    it.name.contains(state.query.text)
                }
                state.searching = false
            }
            when (state.searchDisplay) {
                SearchDisplay.Suggestions -> SuggestionsContent(state.suggestions, onItemClick)
                SearchDisplay.Results -> ResultsContent(state, onItemClick)
                SearchDisplay.NoResults -> NoResultsContent(query = state.query.text)
            }
        }
    }
}


enum class SearchDisplay {
    Suggestions, Results, NoResults
}


@Composable
private fun rememberSearchState(
    query: TextFieldValue = TextFieldValue(""),
    focused: Boolean = false,
    searching: Boolean = false,
    suggestions: List<SchoolResponse.Data.School> = emptyList(),
    searchResults: List<SchoolResponse.Data.School> = emptyList()
): SearchState {
    return remember {
        SearchState(
            query = query,
            focused = focused,
            searching = searching,
            suggestions = suggestions,
            searchResults = searchResults
        )
    }
}

@Stable
class SearchState(
    query: TextFieldValue,
    focused: Boolean,
    searching: Boolean,
    suggestions: List<SchoolResponse.Data.School>,
    searchResults: List<SchoolResponse.Data.School>
) {
    var query by mutableStateOf(query)
    var focused by mutableStateOf(focused)
    var searching by mutableStateOf(searching)

    var suggestions by mutableStateOf(suggestions)
    var searchResults by mutableStateOf(searchResults)
    val searchDisplay: SearchDisplay
        get() = when {
            query.text.isEmpty() -> SearchDisplay.Suggestions
            searchResults.isEmpty() -> SearchDisplay.NoResults
            else -> SearchDisplay.Results
        }
}

@Composable
fun mirroringIcon(ltrIcon: ImageVector, rtlIcon: ImageVector): ImageVector =
    if (LocalLayoutDirection.current == LayoutDirection.Ltr) ltrIcon else rtlIcon

@Composable
fun mirroringBackIcon() = mirroringIcon(
    ltrIcon = Icons.Outlined.ArrowBack, rtlIcon = Icons.Outlined.ArrowForward
)


@Composable
private fun SearchBar(
    query: TextFieldValue,
    onQueryChange: (TextFieldValue) -> Unit,
    searchFocused: Boolean,
    onSearchFocusChange: (Boolean) -> Unit,
    onClearQuery: () -> Unit,
    searching: Boolean,
    modifier: Modifier = Modifier
) {
    SignCloudSurface(
        color = SignCloudTheme.colors.uiFloated,
        contentColor = SignCloudTheme.colors.textSecondary,
        shape = MaterialTheme.shapes.small,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 24.dp, vertical = 8.dp)
    ) {
        Box(Modifier.fillMaxSize()) {
            if (query.text.isEmpty()) {
                SearchHint()
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight()
            ) {
                if (searchFocused) {
                    IconButton(onClick = onClearQuery) {
                        Icon(
                            imageVector = mirroringBackIcon(),
                            tint = SignCloudTheme.colors.iconPrimary,
                            contentDescription = stringResource(id = R.string.back)
                        )
                    }
                } else {
                    Spacer(Modifier.width(IconSize)) // balance arrow icon
                }
                BasicTextField(
                    value = query,
                    onValueChange = onQueryChange,
                    modifier = Modifier
                        .weight(1f)
                        .onFocusChanged {
                            onSearchFocusChange(it.isFocused)
                        },
                    singleLine = true
                )
                if (searching) {
                    CircularProgressIndicator(
                        color = SignCloudTheme.colors.iconPrimary,
                        modifier = Modifier
                            .padding(horizontal = 6.dp)
                            .size(36.dp)
                    )
                }
            }
        }
    }
}

private val IconSize = 48.dp

@Composable
private fun SearchHint() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize()
    ) {
        Icon(
            imageVector = Icons.Outlined.Search,
            tint = SignCloudTheme.colors.textHelp,
            contentDescription = stringResource(R.string.search)
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = stringResource(R.string.search_school),
            color = SignCloudTheme.colors.textHelp
        )
    }
}


@Composable
fun SuggestionsContent(
    schools: List<SchoolResponse.Data.School>,
    onItemClick: (String, String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize()
            .padding(16.dp)
    ) {
        SchoolsList(schools = schools, onItemClick = onItemClick)
    }

}

@Composable
fun ResultsContent(
    state: SearchState = rememberSearchState(),
    onItemClick: (String, String) -> Unit
) {
    val schools = state.suggestions.filter {
        it.name.contains(state.query.text)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize()
            .padding(16.dp)
    ) {
        SchoolsList(schools = schools, onItemClick = onItemClick)
    }

}

@Composable
fun SchoolsList(
    modifier: Modifier = Modifier,
    schools: List<SchoolResponse.Data.School>,
    onItemClick: (String, String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.Top,
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize()
    ) {
        val schoolSelected = remember { mutableStateOf(0) }
        val collegeSelected = remember { mutableStateOf(0) }
        val schoolsState = remember { mutableStateOf(schools) }
        val collegesState = remember { mutableStateOf(emptyList<SchoolResponse.Data.College>()) }
        Box(
            modifier
                .padding(4.dp, 0.dp)
                .fillMaxSize()
                .weight(2f)
        ) {
            LazyColumn {
                itemsIndexed(schoolsState.value) { index, school ->
                    Spacer(modifier.height(8.dp))
                    if (schoolSelected.value == index) {
                        Text(
                            text = school.name,
                            fontSize = 18.sp,
                            color = SignCloudTheme.colors.primary
                        )
                    } else {
                        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                            Text(
                                text = school.name,
                                fontSize = 18.sp,
                                modifier = modifier.clickable { schoolSelected.value = index }
                            )
                        }
                    }
                }
            }
        }
        SignCloudDivider(
            modifier = modifier
                .fillMaxHeight()
                .width(1.dp)
        )

        collegesState.value =
            schoolsState.value.getOrNull(schoolSelected.value)?.colleges ?: emptyList()
        Box(
            modifier = modifier
                .padding(24.dp, 0.dp, 0.dp, 0.dp)
                .fillMaxSize()
                .weight(3f)
        ) {
            LazyColumn {
                itemsIndexed(collegesState.value) { index, college ->
                    Spacer(modifier.height(8.dp))
                    if (collegeSelected.value == index) {
                        Text(text = college.name, fontSize = 17.sp)
                    } else {
                        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                            Text(
                                text = college.name,
                                fontSize = 17.sp,
                                modifier = modifier.clickable {
                                    collegeSelected.value = index
                                })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NoResultsContent(
    modifier: Modifier = Modifier,
    query: String,
    lightTheme: Boolean = MaterialTheme.colors.isLight,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize()
            .padding(24.dp)
    ) {
        val assetId = if (lightTheme) {
            R.drawable.empty_state_search_light
        } else {
            R.drawable.empty_state_search_dark
        }
        Image(
            painter = painterResource(id = assetId),
            contentDescription = null
        )
        Spacer(Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.search_no_matches, query),
            style = MaterialTheme.typography.subtitle1,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.search_no_matches_retry),
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Preview("Search Bar")
@Composable
private fun SearchBarPreview() {
    SignCloudTheme {
        SignCloudSurface {
            SearchBar(
                query = TextFieldValue(""),
                onQueryChange = { },
                searchFocused = false,
                onSearchFocusChange = { },
                onClearQuery = { },
                searching = false
            )
        }
    }
}

@Preview("Search Bar • Dark")
@Composable
private fun SearchBarDarkPreview() {
    SignCloudTheme(darkTheme = true) {
        SignCloudSurface() {
            SearchBar(
                query = TextFieldValue(""),
                onQueryChange = { },
                searchFocused = false,
                onSearchFocusChange = { },
                onClearQuery = { },
                searching = false
            )
        }
    }
}