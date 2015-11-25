package com.dzhenyu.test.http.request;

import java.util.List;

/**
 * Created by onlymem on 2015/11/3.
 */
public class Students extends MMRequest {

    public List<Student> studentQuery;

    public static class Student {
       public  Student(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String name;
        public int age;
    }
}
