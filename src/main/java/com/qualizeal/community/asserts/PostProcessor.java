package com.qualizeal.community.asserts;

import java.util.List;

public interface PostProcessor<T> {
    void setErrors(List<T> errors);
    List<T> getErrors();
    T process();
    void fail();
}
