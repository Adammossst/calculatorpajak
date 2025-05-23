package com.adamchaniago0025.calculatorpajak.ui.screen

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.adamchaniago0025.calculatorpajak.R
import com.adamchaniago0025.calculatorpajak.navigation.Screen
import com.adamchaniago0025.calculatorpajak.ui.theme.CalculatorpajakTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController){
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screen.About.route)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = "Localized description"
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
    ) { innerPadding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)) {
            KalkulatorPajakScreen()
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CalculatorpajakTheme {
        Greeting("Android")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KalkulatorPajakScreen() {
    var jumlahTagihan by remember { mutableStateOf("") }
    var persentasePajak by remember { mutableStateOf("10%") }
    var totalSetelahPajak by remember { mutableDoubleStateOf(0.0) }
    var showResult by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    var jumlahTagihanError by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val daftarPajak = listOf("5%", "10%","12%", "15%", "20%", "25%", "50%")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.tax),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            alpha = 0.8f,
            modifier = Modifier
        )
        OutlinedTextField(
            value = jumlahTagihan,
            supportingText = { ErrorHint(jumlahTagihanError) },
            isError = jumlahTagihanError,
            onValueChange = { jumlahTagihan = it },
            label = { Text(stringResource(R.string.input_bill)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth().padding(top = 80.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = persentasePajak,
                onValueChange = {},
                readOnly = true,
                label = { Text(stringResource(R.string.select_tax)) },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)

                },

                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                daftarPajak.forEach { pajak ->
                    DropdownMenuItem(
                        text = { Text(pajak) },
                        onClick = {
                            persentasePajak = pajak
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                jumlahTagihanError = jumlahTagihan.trim().isEmpty() ||
                        jumlahTagihan.toDoubleOrNull() == null ||
                        jumlahTagihan.toDouble() <= 0

                if (!jumlahTagihanError) {
                    val tagihan = jumlahTagihan.toDoubleOrNull() ?: 0.0
                    val pajak = persentasePajak.replace("%", "").toDoubleOrNull() ?: 0.0
                    totalSetelahPajak = tagihan + (tagihan * (pajak / 100))
                    showResult = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(text = stringResource(R.string.calculate))
        }


        Spacer(modifier = Modifier.height(24.dp))

        if (showResult) {
            Text(
                text = "${stringResource(R.string.result)} Rp$totalSetelahPajak",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.secondary
            )
        }
        Button(
            onClick = {shareData(
                context = context,
                message = context.getString(
                    R.string.share_template, jumlahTagihan, persentasePajak, totalSetelahPajak
                )
            )},
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.share)

            )
        }
    }
}

private fun shareData(context:Context, message: String) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }
    if (shareIntent.resolveActivity(context.packageManager) !=null) {
        context.startActivity(shareIntent)
    }
}
@Composable
fun ErrorHint(isError: Boolean) {
    if (isError){
        Text(
            text = stringResource(R.string.input_invalid),
            color = MaterialTheme.colorScheme.error
        )
    }
}