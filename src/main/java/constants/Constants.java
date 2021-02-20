package constants;

public interface Constants {

    String LIST_USERS = "/api/users?page=2";
    String SINGLE_USER = "/api/users/2";
    String SINGLE_USER_NOT_FOUND = "/api/users/23";
    String LIST_RESOURCE = "/api/unknown";
    String LOGIN = "/api/login";
    String SINGLE_RESOURCE = "/api/unknown/2";
    String SINGLE_RESOURCE_404 = "/api/unknown/23";
    String CREATE = "/api/users";
    String PUT = "/api/users/2";
    String REGISTER_SUCCESSFUL = "/api/register";
    String DELAYED = "/api/users?delay=3";
}
