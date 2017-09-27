package g55p.github.com.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import g55p.github.com.jmapper.JMapper;
import g55p.github.com.sample.jmapper_obj.Student;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JMapper.getInstance().mapJSON("https://benighted.000webhostapp.com/jmapper/test.json", Student.class, new JMapper.IOnJSONLoaded() {
            @Override
            public void onLoad(ArrayList<Object> arrayList) {
                for (Object obj : arrayList) {
                    Student student = (Student) obj;
                    Log.d("Student", "id:" + student.id + "-name:" + student.name + "-lastName:" + student.lastName + "-age:" + student.age);

                }
            }
        });

    }
}
