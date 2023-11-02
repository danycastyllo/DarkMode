package com.example.darkmode

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.darkmode.ui.theme.Gray80
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawerHeader(darkTheme: Boolean, onThemeUpdated: () -> Unit) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val items = listOf(
        DrawerItem(icon = Icons.Default.Favorite, label = "Likes", secondaryLabel = "64"),
        DrawerItem(icon = Icons.Default.Face, label = "Messages", secondaryLabel = "64"),
        DrawerItem(icon = Icons.Default.Email, label = "Email", secondaryLabel = "64"),
        DrawerItem(icon = Icons.Default.Settings, label = "Settings", secondaryLabel = ""),
    )
    var selectedItem by remember { mutableStateOf(items[0]) }
    // dropdown menu
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen, // allows you to move the menu to close it only when it is open
        drawerContent = {
            ModalDrawerSheet {
                Box(// box containing header text
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 64.dp),
                    contentAlignment = Alignment.Center
                ){
                    Text(text = "Header", style = MaterialTheme.typography.headlineLarge)
                }
                // dropdown menu items
                items.forEach{ item ->
                    NavigationDrawerItem(
                        label = { Text(text = item.label, color = MaterialTheme.colorScheme.onPrimary) },
                        selected = item == selectedItem,
                        onClick = {
                            scope.launch{drawerState.close()}
                            selectedItem = item
                        },
                        icon = {Icon(imageVector = item.icon, contentDescription = item.label, tint = MaterialTheme.colorScheme.onPrimary)},
                        badge = { Text(text = item.secondaryLabel, color = MaterialTheme.colorScheme.onPrimary)},
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp),
                    contentAlignment = Alignment.Center
                ){
                    ThemeSwitcher( // creating the switch for dark mode
                        darkTheme = darkTheme,
                        size = 50.dp,
                        padding = 5.dp,
                        onClick = onThemeUpdated
                    )
                }

            }
        },
        content = {
            Content (
                onMenuIconClick = {scope.launch{drawerState.open()}}
            )
        }
    )
}

data class DrawerItem(
    val icon: ImageVector,
    val label: String,
    val secondaryLabel: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Content(
    onMenuIconClick: () -> Unit
) {
    Scaffold (
        topBar = {
            Surface(modifier = Modifier, shadowElevation = 6.dp, color = MaterialTheme.colorScheme.primary){
                TopAppBar(
                    navigationIcon = {
                        IconButton(onClick = onMenuIconClick) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Open Drawer")
                        }
                    },
                    title = { Text(text = "Top Stories")},

                )
            }
        }
    ){padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ){
            MyCustomCard(
                modifier = Modifier.fillMaxWidth(0.8f),
                publisher = Publisher(
                    name = "Andrea Ortiz",
                    job = "Android Developer",
                    image = R.drawable.myphoto
                ),
                text = "Lorem ipsum dolor, sit amet consectetur adipisicing elit. Tenetur provident velit consectetur maiores voluptates? Obcaecati officiis repudiandae quod ex quis provident quae maiores quisquam vitae voluptatem. Est a cum repellat?"
            )
        }

    }
}

@Composable
fun ThemeSwitcher(
    darkTheme: Boolean = false,
    size: Dp = 150.dp,
    iconSize: Dp = size / 2,
    padding: Dp = 10.dp,
    borderWidth: Dp = 1.dp,
    parentShape: Shape = CircleShape,
    toggleShape: Shape = CircleShape,
    animationSpec: AnimationSpec<Dp> = tween(durationMillis = 300),
    onClick: () -> Unit
) {
    val offset by animateDpAsState(
        targetValue = if (darkTheme) 0.dp else size,
        animationSpec = animationSpec
    )

    Box(modifier = Modifier
        .width(size * 2)
        .height(size)
        .clip(shape = parentShape)
        .clickable { onClick() }
        .background(MaterialTheme.colorScheme.tertiary)
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .offset(x = offset)
                .padding(all = padding)
                .clip(shape = toggleShape)
                .background(Gray80)
        ) {}
        Row(
            modifier = Modifier
                .border(
                    border = BorderStroke(
                        width = borderWidth,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    shape = parentShape
                )
        ) {
            Box(
                modifier = Modifier.size(size),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(iconSize),
                    painter = painterResource(R.drawable.baseline_dark_mode_24),
                    contentDescription = "Theme Icon",
                    tint = Gray80
                )
            }
            Box(
                modifier = Modifier.size(size),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(iconSize),
                    painter = painterResource(R.drawable.baseline_wb_sunny_24),
                    contentDescription = "Theme Icon",
                    tint = Gray80
                )
            }
        }
    }
}