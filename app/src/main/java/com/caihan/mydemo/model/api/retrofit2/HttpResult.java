package com.caihan.mydemo.model.api.retrofit2;

/**
 * Created by caihan on 2017/1/11.
 */
public class HttpResult<T> {
    private static final String TAG = "RfRequst";
    /**
     * request_id : null
     * status_code : 200
     * message : 成功
     * success : true
     * data : []
     */

    private Object request_id;
    private String status_code;
    private String message;
    private boolean success;
    private T data;

    public Object getRequest_id() {
        return request_id;
    }

    public void setRequest_id(Object request_id) {
        this.request_id = request_id;
    }

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "request_id=" + request_id +
                ", status_code='" + status_code + '\'' +
                ", message='" + message + '\'' +
                ", success=" + success +
                ", data=" + data +
                '}';
    }
}
