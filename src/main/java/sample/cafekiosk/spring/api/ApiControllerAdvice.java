package sample.cafekiosk.spring.api;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ApiControllerAdvice {

    /**
     * @ResponseStatus 빼먹지 않기 주의 -> 해당 어드바이스에 대한 상태값 설정
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ApiResponse<Object> bindException(BindException e) {
        return ApiResponse.of(
                HttpStatus.BAD_REQUEST,
                // 첫 번째 에러의 메시지 꺼내기..
                e.getBindingResult().getAllErrors().get(0).getDefaultMessage()
        );
    }
}
