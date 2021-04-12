package uz.hashteam.turontask.module

import org.koin.dsl.module
import uz.hashteam.turontask.list.main.adapter.MainAdapter
import uz.hashteam.turontask.util.FileManager
import uz.hashteam.turontask.util.Prefs

val appModule = module {
    single { Prefs(get()) }
    single { FileManager(get()) }
}

val mainModule = module {
    single { MainAdapter(get(), get()) }
}