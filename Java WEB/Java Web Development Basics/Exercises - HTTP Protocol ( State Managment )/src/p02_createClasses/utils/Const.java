package p02_createClasses.utils;

public final class Const {

    private Const() {

    }

    public static final String FIRST_LINE_PATTERN = "%s %s%s";
    public static final String DATE_HEADER_PATTERN = "Date: %s";
    public static final String DATE_FORMAT_PATTERN = "dd/MM/yyyy";
    public static final String EMPTY_STRING = "";
    public static final String PROCESS_HEADER_DATE = "Date";
    public static final String COOKIE = "Cookie";
    public static final String COOKIE_PATTERN = "%s <-> %s";

    public static final String POST = "POST";

    public static final String DEFAULT_HTTP_RES_VERSION = "HTTP/1.1";

    public static final int STATUS_CODE_200 = 200;
    public static final int STATUS_CODE_201 = 201;
    public static final int STATUS_CODE_303 = 303;
    public static final int STATUS_CODE_400 = 400;
    public static final int STATUS_CODE_401 = 401;
    public static final int STATUS_CODE_403 = 403;
    public static final int STATUS_CODE_404 = 404;
    public static final int STATUS_CODE_500 = 500;

    public static final String STATUS_BAD_REQUEST = "400 Bad Request";
    public static final String STATUS_UNAUTHORIZED = "401 Unauthorized";
    public static final String STATUS_NOT_FOUND = "404 Not Found";
    public static final String STATUS_OK = "200 OK";
    public static final String STATUS_CREATED = "201 Created";
    public static final String STATUS_INTERNAL_SERVER_ERROR = "500 Internal Server Error";
    public static final String STATUS_FORBIDDEN = "403 Forbidden";
    public static final String STATUS_SEE_OTHER = "303 See Other";
}
