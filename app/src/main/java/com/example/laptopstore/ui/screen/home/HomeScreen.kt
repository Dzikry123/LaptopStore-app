package com.example.laptopstore.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.laptopstore.di.Injection
import com.example.laptopstore.ui.common.UiState
import com.example.laptopstore.ViewModelFactory
import com.example.laptopstore.model.OrderLaptop
import com.example.laptopstore.ui.components.LaptopItem

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllLaptops()
            }
            is UiState.Success -> {
                Column() {
                    HomeContent(
                        orderLaptop = uiState.data,
                        modifier = modifier,
                        navigateToDetail = navigateToDetail,
                    )
                }
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun HomeContent(
    orderLaptop: List<OrderLaptop>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
) {
    val filteredList = remember {
        mutableStateOf(listOf<OrderLaptop>())
    }

    LaunchedEffect(orderLaptop) {
        filteredList.value = orderLaptop
    }

    val searchQuery = remember { mutableStateOf("") }

    Column {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Search")
            },
            value = searchQuery.value,
            onValueChange = { newValue ->
                searchQuery.value = newValue
                filteredList.value = if (newValue.isBlank()) {
                    orderLaptop
                } else {
                    orderLaptop.filter {
                        it.laptop.title.contains(newValue, ignoreCase = true)
                    }
                }
            },
            label = { Text("Search") }
        )

        if (filteredList.value.isEmpty()) {
            Text("Data tidak ditemukan")
        } else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(160.dp),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = modifier.testTag("OrderList")
            ) {
                items(filteredList.value) { data ->
                    LaptopItem(
                        image = data.laptop.image,
                        title = data.laptop.title,
                        requiredPrice = data.laptop.price,
                        modifier = Modifier.clickable {
                            navigateToDetail(data.laptop.id)
                        }
                    )
                }
            }
        }
    }
}
