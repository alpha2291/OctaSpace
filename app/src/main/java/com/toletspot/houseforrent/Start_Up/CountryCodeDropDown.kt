package com.toletspot.houseforrent.Start_Up

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.gson.Gson
import java.io.InputStreamReader

data class Country(
    val name: String = "India",
    val dial_code: String = "+91",
    val code: String = "IN",
    val emoji: String = "\uD83C\uDDEE\uD83C\uDDF3",
    val limit : Int = 10
)

// 2️⃣ Helper function to load from assets
fun loadCountries(context: Context): List<Country> {
    return try {
        val inputStream = context.assets.open("countrycode.json")
        val reader = InputStreamReader(inputStream)
        Gson().fromJson(reader, Array<Country>::class.java).toList()
    } catch (e: Exception) {
        e.printStackTrace()
        emptyList()
    }
}



// 3️⃣ Bottom sheet composable
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryPickerBottomSheet(
    context: Context,
    showSheet: Boolean,
    onDismiss: () -> Unit,
    onSelect: (Country) -> Unit,
    viewModel: Start_Up_ViewModel
) {
    if (!showSheet) return

    val countries by remember { mutableStateOf(loadCountries(context)) }

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        sheetGesturesEnabled = false,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        containerColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = "Select Country",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.Black,
                modifier = Modifier.padding(vertical = 12.dp)
            )

            Divider(color = Color(0xFFE0E0E0))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 500.dp)
            ) {
                items(countries) { country ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onSelect(country)
                                viewModel.add_selectedCountry(country)
                                viewModel.phoneNumber = ""
                                onDismiss()
                            }
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                        , horizontalArrangement = Arrangement.SpaceBetween
                    ) {

//                        Box(
//                            modifier = Modifier
//                                .size(25.dp)
//                                .clip(CircleShape)
//                                .background(Color.LightGray)
//                        ) {
                            Text(
                                text = country.emoji,
                                fontSize = 24.sp
                            )
//                        }
//                        Spacer(Modifier.width(12.dp))
//                        Column {
                            Text(
                                text = country.name,
                                fontSize = 16.sp,
                                color = Color.Black
                            )

                            Text(
                                text = country.dial_code,
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
//                        }
                    }
                   // Divider(color = Color(0xFFE0E0E0))
                }
            }
        }
    }
}
