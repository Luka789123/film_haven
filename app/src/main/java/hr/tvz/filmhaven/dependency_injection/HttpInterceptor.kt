package hr.tvz.filmhaven.dependency_injection

import hr.tvz.filmhaven.core.Constants
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class HttpInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
       val request:Request = chain
           .request()
           .newBuilder()
           .addHeader("Authorization",Constants.AUTHENTICATION_TOKEN)
           .build()
        return chain.proceed(request);
    }
}