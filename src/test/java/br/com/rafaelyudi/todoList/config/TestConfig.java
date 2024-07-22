package br.com.rafaelyudi.todoList.config;

public class TestConfig {
    public static final int SERVER_PORT = 8080;
    public static  final String HEADER_PARAM_ORIGIN = "origin"; 
    public static final String HEADER_PARAM_AUTHORIZATION = "authorization";
    
    public static final String ALLOWED_DOMAIN = "http://localhost:4200";
    public static final String NOT_ALLOWED_DOMAIN = "http://randomdomain";

    public static final String MEDIA_TYPE_JSON = "application/json";
    public static final String MEDIA_TYPE_XML = "application/xml";

    public static final String basePathUser = "/users/v1";

    public static final String basePathTask = "/tasks/v1";

}
