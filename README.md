# jmapper
<br/>
<b>How it works</b>
<br/>
As a summary of this library, we can say that assume a class which includes some fields which be <b>public</b> and also we've declared an annotation above of each of them.
<br/>
as an example:

```java
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
    public ArrayList<Score> scores; //This is used to map JSON Array in a JSON Object
}
```
<br/>
used JSON for this example:
<br/>

```json
[
{
"id":1,
"name":"John",
"lastName":"branic",
"age":22,
"scores":[
{"date":"2.2.2005","score":14}
,{"date":"2.2.2005","score":15}
,{"date":"2.2.2005","score":16}
]
},
{
"id":2,
"name":"eric",
"lastName":"branic",
"age":23,
"scores":[
{"date":"2.2.2005","score":12}
,{"date":"2.2.2005","score":3}
,{"date":"2.2.2005","score":18}
]
}
]
```


JMapper sets field value according to its annotation value. for example it sets value of id in JSON to id (INT).<br/>
To get JSON from URL:
```java
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
<br/>

```groovy
compile 'com.github.g55p.jmapper:jmapper:1.0.0'
```
<br/>
in your AndroidManifest.xml

```xml
<uses-permission android:name="android.permission.INTERNET"/>
```

