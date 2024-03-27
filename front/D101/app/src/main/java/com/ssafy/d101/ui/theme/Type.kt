package com.ssafy.d101.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.ssafy.d101.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val nanumgothic = GoogleFont("Nanum Square")

val fontFamily = FontFamily(
    Font(R.font.nanum_gothic, FontWeight.Normal),
    Font(R.font.nanum_gothic_bold, FontWeight.Bold),
    Font(R.font.nanum_gothic_light, FontWeight.Light),
    Font(R.font.nanum_gothic_extra_bold, FontWeight.ExtraBold),
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = fontFamily,
    ),
    bodySmall = TextStyle(
        fontFamily = fontFamily,
    ),
    bodyMedium = TextStyle(
        fontFamily = fontFamily,
    ),
    headlineLarge = TextStyle(
        fontFamily = fontFamily
    ),
    headlineSmall = TextStyle(
        fontFamily = fontFamily
    ),
    displayLarge = TextStyle(
        fontFamily = fontFamily
    ),
    displaySmall = TextStyle(
        fontFamily = fontFamily
    ),
    displayMedium = TextStyle(
        fontFamily = fontFamily
    ),
    headlineMedium = TextStyle(
        fontFamily = fontFamily
    ),
    labelLarge = TextStyle(
        fontFamily = fontFamily
    ),
    labelMedium = TextStyle(
        fontFamily = fontFamily
    ),
    labelSmall = TextStyle(
        fontFamily = fontFamily
    ),
    titleLarge = TextStyle(
        fontFamily = fontFamily
    ),
    titleMedium = TextStyle(
        fontFamily = fontFamily
    ),
    titleSmall = TextStyle(
        fontFamily = fontFamily
    ),


    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)