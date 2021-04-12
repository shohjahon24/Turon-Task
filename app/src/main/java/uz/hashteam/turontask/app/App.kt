package uz.hashteam.turontask.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin
import uz.hashteam.turontask.module.appModule
import uz.hashteam.turontask.module.mainModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            fragmentFactory()
            androidContext(applicationContext)
            modules(appModule, mainModule)
        }
    }
}