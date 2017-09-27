package g55p.github.com.sample.jmapper_obj;


import java.util.ArrayList;

import g55p.github.com.jmapper.JSONKey;

public class Student {
    @JSONKey("id")
    public int id;
    @JSONKey("name")
    public String name;
    @JSONKey("lastName")
    public String lastName;
    @JSONKey("age")
    public int age;
    @JSONKey("scores")
    public ArrayList<Score> scores;
}
