package com.coding.newsapp.ui.bookmark



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
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.font.FontWeight
import com.coding.newsapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookMarkAppBar(modifier: Modifier = Modifier
,appBarTitle:String,
                   onSettingClick:()-> Unit={},
                   onFilterListClick:()-> Unit={},
                   onSearchClick:()-> Unit={},
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
                onSettingClick()
            }) {
              Icon(imageVector = Icons.Default.Settings,
                  contentDescription = appBarTitle)
            }
            IconButton({
                onFilterListClick
            }) {
                Icon(painter = painterResource(R.drawable.baseline_filter_list_24),
                    contentDescription = " open filter list")
            }

            IconButton({
                onSearchClick
            }) {
                Icon(painter = painterResource(R.drawable.ic_search),
                    contentDescription = " open search Bar")
            }
        }
    )

}