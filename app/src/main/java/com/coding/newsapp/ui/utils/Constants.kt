package com.coding.newsapp.ui.utils
import android.app.Activity
import android.content.Intent
import androidx.annotation.StringRes
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.coding.newsapp.R
import com.coding.newsapp.ui.navigation.NavigationItem
import com.coding.newsapp.ui.navigation.Route
import java.util.UUID


fun shareLink(url: String) {
    val sendIntent = Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_TEXT, url)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, "Share Link")
    activityProvider.invoke().startActivity(shareIntent)
}

private var activityProvider: () -> Activity = {
    throw IllegalArgumentException("Error")
}

fun setActivityProvider(provider: () -> Activity) {
    activityProvider = provider
}
enum class Type {
    Mobile, Desktop
}

data class Size(
    val width: Dp,
    val height: Dp
)



 fun getType(): Type {
    return Type.Mobile
}

@Composable
 fun getScreenSize(): Size {

    val configuration = LocalConfiguration.current
    val screenHeightDp = configuration.screenHeightDp.dp
    val screenWidthDP = configuration.screenWidthDp.dp
    return Size(width = screenWidthDP, height = screenHeightDp)
}
const val dataStoreFileName = "setting.preferences_pb"
///////////////////////////////////////////////////////////////////////
val categoryList=arrayListOf(
    "Business",
    "Entertainment",
    "General",
    "Health",
    "Science",
    "Sports",
    "Technology"
)
///////////////////////////////////////////////////////
val FadeIn = fadeIn(animationSpec = tween(220, delayMillis = 90)) +
        scaleIn(
            initialScale = 0.92f,
            animationSpec = tween(220, delayMillis = 90)
        )

val FadeOut = fadeOut(animationSpec = tween(90))

////////////////////////////////////////////////////////////////////////////
val navigationItemsLists = listOf(
    NavigationItem(
        icon = R.drawable.ic_headline,
        title = R.string.headlines,
        route = Route.Headline
    ),
    NavigationItem(
        icon = R.drawable.ic_search,
        title = R.string.search,
        route = Route.Search,
    ),
    NavigationItem(
        icon = R.drawable.ic_bookmark_outlined,
        title = R.string.bookmark,
        route = Route.Bookmark,
    ),
)




//////////////////////////////////////////////////////

enum class Theme(val titleRes: Int) {
    SYSTEM_DEFAULT(R.string.system_default),
    LIGHT_MODE(R.string.light_mode),
    DARK_MODE(R.string.dark_mode)
}




enum class Language(@StringRes val labelRes: Int) {
    DEFAULT_LANGUAGE(R.string.system_default_language),
    ENGLISH(R.string.english),
    ARABIC(R.string.arabic)
}


