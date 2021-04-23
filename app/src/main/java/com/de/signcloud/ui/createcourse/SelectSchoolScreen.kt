package com.de.signcloud.ui.createcourse

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.isFocused
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.de.signcloud.R
import com.de.signcloud.ui.components.SignCloudDivider
import com.de.signcloud.ui.components.SignCloudSurface
import com.de.signcloud.ui.theme.SignCloudTheme
import com.google.accompanist.insets.statusBarsPadding

sealed class SelectSchoolEvent {

}

@Composable
fun SelectSchool(
    onSchoolClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    SignCloudSurface(modifier = modifier.fillMaxSize()) {
        Column {
            Spacer(modifier = Modifier.statusBarsPadding())

            SignCloudDivider()

//            LaunchedEffect() {}
        }
    }
}


enum class SearchDisplay {
    Categories, Suggestions, Results, NoResults
}

@Composable
private fun rememberSearchState(
    query: TextFieldValue = TextFieldValue(""),
    focused: Boolean = false,
    searching: Boolean = false,
//    categories: List<SearchCategoryCollection> = SearchRepo.getCategories(),
//    suggestions: List<SearchSuggestionGroup> = SearchRepo.getSuggestions(),
//    filters: List<Filter> = SnackRepo.getFilters(),
//    searchResults: List<Courses> = emptyList()
): SearchState {
    return remember {
        SearchState(
            query = query,
            focused = focused,
            searching = searching,
//            categories = categories,
//            suggestions = suggestions,
//            filters = filters,
//            searchResults = searchResults
        )
    }
}

@Stable
class SearchState(
    query: TextFieldValue,
    focused: Boolean,
    searching: Boolean,
//    categories: List<SearchCategoryCollection>,
//    suggestions: List<SearchSuggestionGroup>,
//    filters: List<Filter>,
//    searchResults: List<Snack>
) {
    var query by mutableStateOf(query)
    var focused by mutableStateOf(focused)
    var searching by mutableStateOf(searching)
//    var categories by mutableStateOf(categories)
//    var suggestions by mutableStateOf(suggestions)
//    var filters by mutableStateOf(filters)
//    var searchResults by mutableStateOf(searchResults)
    val searchDisplay: SearchDisplay
        get() = when {
            !focused && query.text.isEmpty() -> SearchDisplay.Categories
            focused && query.text.isEmpty() -> SearchDisplay.Suggestions
//            searchResults.isEmpty() -> SearchDisplay.NoResults
            else -> SearchDisplay.Results
        }
}

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
                            imageVector = Icons.Outlined.ArrowBack,
                            tint = SignCloudTheme.colors.iconPrimary,
                            contentDescription = stringResource(id = R.string.back)
                        )
                    }
                }
                BasicTextField(
                    value = query,
                    onValueChange = onQueryChange,
                    modifier = Modifier
                        .weight(1f)
                        .onFocusChanged {
                            onSearchFocusChange(it.isFocused)
                        }
                )
                if (searching) {
                    CircularProgressIndicator(
                        color = SignCloudTheme.colors.iconPrimary,
                        modifier = Modifier
                            .padding(horizontal = 6.dp)
                            .size(36.dp)
                    )
                } else {
                    Spacer(Modifier.width(IconSize)) // balance arrow icon
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