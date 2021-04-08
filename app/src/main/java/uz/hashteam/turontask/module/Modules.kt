package uz.hashteam.turontask.module

import org.koin.dsl.module
import uz.hashteam.turontask.util.Prefs

val appModule = module {
    single { Prefs(get()) }
}