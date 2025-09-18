package com.coding.newsapp.ui




import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(modifier: Modifier = Modifier
,appBarTitle:String,onClick:()-> Unit
) {
    TopAppBar(
        title = {
            Text(text = appBarTitle,
              style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold)
        }
        ,
        actions = {
            IconButton(onClick = {
                onClick()
            }) {
              Icon(imageVector = Icons.Default.Settings,
                  contentDescription = appBarTitle)
            }

        }
    )

}