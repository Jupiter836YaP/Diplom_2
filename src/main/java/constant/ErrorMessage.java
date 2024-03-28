package constant;

public class ErrorMessage {
    public static final String MESSAGE_FOR_CREATE_EXIST_USER = "User already exists";
    public static final String MESSAGE_FOR_CREATE_USER_WITHOUT_FIELD = "Email, password and name are required fields";
    public static final String MESSAGE_FOR_AUTH_INCORRECT_DATA = "email or password are incorrect";
    public static final String MESSAGE_FOR_UPDATE_WITHOUT_AUTH = "You should be authorised";
    public static final String MESSAGE_FOR_CREATE_ORDER_WITHOUT_INGREDIENTS = "Ingredient ids must be provided";
    public static final String MESSAGE_FOR_GET_ORDERS_WITHOUT_AUTH = "You should be authorised";
}
