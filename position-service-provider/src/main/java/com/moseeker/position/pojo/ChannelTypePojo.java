package com.moseeker.position.pojo;

import java.util.Objects;

public class ChannelTypePojo {
    private int code;
    private String text;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChannelTypePojo that = (ChannelTypePojo) o;
        return getCode() == that.getCode() &&
                Objects.equals(getText(), that.getText());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getCode(), getText());
    }
}
