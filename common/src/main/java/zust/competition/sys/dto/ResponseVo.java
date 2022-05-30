package zust.competition.sys.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
public class ResponseVo {

    /**
     * HTTP状态码 默认 200
     */
    private Integer code = 200;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 服务器时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date time = new Date();

    /**
     * 返回的数据
     */
    private Object result;

    public ResponseVo() {
        this.code = HttpStatus.OK.value();
        this.message = "success";
    }

    public ResponseVo(Object data) {
        this.message = "success";
        this.result = data;
    }

    public ResponseVo(Object data, String msg) {
        this.message = msg;
        this.result = data;
    }

    public ResponseVo(int code, String msg, Object data) {
        this.code = code;
        this.message = msg;
        this.result = data;
    }

    public static ResponseVo ok() {
        return new ResponseVo();
    }

    public static ResponseVo ok(Object data) {
        return new ResponseVo(data);
    }

    public static ResponseVo ok(Object data, String msg) {
        return new ResponseVo(data, msg);
    }


    public static ResponseVo create(int code, String msg) {
        return create(code, msg, null);
    }

    public static ResponseVo create(int code, String msg, Object data) {
        return new ResponseVo(code, msg, data);
    }
}
