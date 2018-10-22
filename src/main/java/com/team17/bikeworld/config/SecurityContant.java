package com.team17.bikeworld.config;

public class SecurityContant {
    public static final String SECRET = "SecretKeyToGenJWTs";
//    public static final long EXPIRATION_TIME = 864000000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/signup";
    public static final String LOG_IN_URL = "/login";

    public static final String JWT_SECRET = "JWTSecretKey";
    public static final String AUTHORITIES_KEY = "JWTAuthoritiesKey";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days

//    public static final String TOKEN_PREFIX = "Bearer ";
//    public static final String HEADER_STRING = "Authorization";
}
