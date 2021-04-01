package com.shailesh.mybusappdagger2.di;

import android.app.Application;
import com.shailesh.mybusappdagger2.BaseApplication;
import com.shailesh.mybusappdagger2.di.webview.WebView_ActivitySubcomponent;

import javax.inject.Singleton;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(
        modules = {
                AndroidSupportInjectionModule.class,
                ActivityBuilderModule.class,
                AppModule.class,
                ViewModelFactoryModule.class
        }
)
public interface AppComponent extends AndroidInjector<BaseApplication> {

    WebView_ActivitySubcomponent.Builder getWebViewSubComponent();

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

}
