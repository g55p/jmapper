# jmapper
<br/>
<b>How it works</b>
<br/>
As a summary of this library, we can say that assume a class which includes some fields which be PUBLIC and also we've declared an annotation above of them.
<br/>
as an example:

```
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
```

JMapper sets field value according to its annotation value. for example it sets value of id in JSON to id (INT).<br/>
To get JSON from URL:
```
     JMapper.getInstance().mapJSON("https://benighted.000webhostapp.com/jmapper/test.json", Student.class, new JMapper.IOnJSONLoaded() {
            @Override
            public void onLoad(ArrayList<Object> arrayList) {
                for (Object obj : arrayList) {
                    Student student = (Student) obj;
                    Log.d("Student", "id:" + student.id + "-name:" + student.name + "-lastName:" + student.lastName + "-age:" + student.age);

                }
            }
        });
```
<br/>
To add this library using gradle:

```
compile 'com.github.g55p.jmapper:jmapper:1.0.0'
```


