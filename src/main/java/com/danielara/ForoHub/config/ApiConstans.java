package com.danielara.ForoHub.config;

public class ApiConstans {
    private ApiConstans() {}
    public static final String BASE_PATH = "${constants.api.url.base-path}";
    public static final String ANSWER_PATH = BASE_PATH +"${constants.api.url.answer}";
    public static final String TOPICS_PATH = BASE_PATH + "${constants.api.url.topics}";
    public static final String USERS_PATH = BASE_PATH + "${constants.api.url.users}";
    public static final String LOGIN_PATH = BASE_PATH + "${constants.api.url.auth}";
}
