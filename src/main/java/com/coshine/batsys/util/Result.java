package com.coshine.batsys.util;

/**
 * Created by jia on 2015/7/5.
 */
public final class Result<T> {

    private String status;
    private String comment;
	private T value;

    public Result(String status, String comment) {
        this.status = status;
        this.comment = comment;
    }

    public Result(T value) {
        this.status = "OK";
        this.value = value;
    }

    public Result(String status, T value) {
        this.status = status;
        this.value = value;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public boolean isOk() {
        return status.equals("OK");
    }

    public boolean isError() {
        return !isOk();
    }

    public static <T> Result<T> create(String status, String comment) {
        return new Result<T>(status, comment);
    }

    public static <T> Result<T> create(T value) {
        return new Result<T>(value);
    }

    public static <T> Result<T> create(String status, T value) {
        return new Result<T>(status, value);
    }
}
