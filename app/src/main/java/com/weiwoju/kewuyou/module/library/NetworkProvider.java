package com.weiwoju.kewuyou.module.library;

import android.content.Context;

import com.weiwoju.kewuyou.Constants;
import com.weiwoju.kewuyou.context.AppCookie;
import com.weiwoju.kewuyou.module.qualifiers.ApplicationContext;
import com.weiwoju.kewuyou.module.qualifiers.CacheDirectory;
import com.weiwoju.kewuyou.module.qualifiers.ShareDirectory;
import com.weiwoju.kewuyou.network.ApiClient;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
    library = true,
    includes = ContextProvider.class
)
public class NetworkProvider {

    @Provides @Singleton
    public ApiClient provideRestApiClient(@CacheDirectory File cacheLocation, @ApplicationContext Context context) {
        ApiClient apiClient = new ApiClient(context, cacheLocation);
        if (AppCookie.isLoggin()) {
//            apiClient.setToken(AppCookie.getAccessToken());
            apiClient.setSessionId(AppCookie.getSessionId());
        }
        return apiClient;
    }

    @Provides @Singleton @CacheDirectory
    public File provideHttpCacheLocation(@ApplicationContext Context context) {
        return context.getCacheDir();
    }

    @Provides @Singleton @ShareDirectory
    public File provideShareLocation(@ApplicationContext Context context) {
        return new File(context.getFilesDir(), Constants.Persistence.SHARE_FILE);
    }
}
