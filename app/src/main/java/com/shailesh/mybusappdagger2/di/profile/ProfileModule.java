package com.shailesh.mybusappdagger2.di.profile;

import com.shailesh.mybusappdagger2.network.ProfileAPi;
import com.shailesh.mybusappdagger2.ui.profile.Profile_Activity;
import com.shailesh.mybusappdagger2.util.progress_dialog.ProgressDialogCustom;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class ProfileModule {

    @ProfileScope
    @Provides
    static ProfileAPi provideProfileApi(Retrofit retrofit)
    {
        return retrofit.create(ProfileAPi.class);
    }

    @ProfileScope
    @Provides
    static ProgressDialogCustom provideProgressDialogCustom(Profile_Activity activity)
    {
        return new ProgressDialogCustom(activity);
    }
}
