package com.shailesh.mybusappdagger2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ApiResponceResource<T> {

    @NonNull
    public final AuthStatus status;

    @Nullable
    public final T data;

    @Nullable
    public final String message;


    public ApiResponceResource(@NonNull AuthStatus status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> ApiResponceResource<T> authenticated (@Nullable T data) {
        return new ApiResponceResource<>(AuthStatus.AUTHENTICATED, data, null);
    }

    public static <T> ApiResponceResource<T> error(@NonNull String msg, @Nullable T data) {
        return new ApiResponceResource<>(AuthStatus.ERROR, data, msg);
    }

    public static <T> ApiResponceResource<T> loading(@Nullable T data) {
        return new ApiResponceResource<>(AuthStatus.LOADING, data, null);
    }

    public static <T> ApiResponceResource<T> logout () {
        return new ApiResponceResource<>(AuthStatus.NOT_AUTHENTICATED, null, null);
    }

    public enum AuthStatus { AUTHENTICATED, ERROR, LOADING, NOT_AUTHENTICATED}
}
