package pan.alexander.finalmap.utils

import android.content.Context
import pan.alexander.finalmap.App

val Context.app: App
    get() = applicationContext as App
