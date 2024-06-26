package sample.cafekiosk.spring.api;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 프론트엔드와 통신하기 용이하게 규격화된 응답 객체가 있으면 좋다.
 * 구조는 딱 정형화되어 있는 것은 아니니 협의하면 됨
 */
@Getter //getter 없으면 406에러 발생
public class ApiResponse<T> {
    private int code;
    private HttpStatus status;
    private String message;
    private T data;

    public ApiResponse(HttpStatus status, String message, T data) {
        this.code = status.value();
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> of(HttpStatus status, String message, T data) {
        return new ApiResponse<>(status, message, data);
    }

    public static <T> ApiResponse<T> of(HttpStatus status, T data) {
        return ApiResponse.of(status, status.name(), data);
    }

    public static <T> ApiResponse<T> ok(T data) {
        return ApiResponse.of(HttpStatus.OK, HttpStatus.OK.name(), data);
    }
}
