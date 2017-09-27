package g55p.github.com.jmapper;


import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JMapper {
    private static JMapper instance = null;

    public interface IOnJSONLoaded {
        void onLoad(ArrayList<Object> arrayList);
    }

    public static JMapper getInstance() {
        if (instance == null)
            instance = new JMapper();
        return instance;
    }

    public void mapJSON(final String url, final Class c, final IOnJSONLoaded onJSONLoaded) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<Object> arrayList = new ArrayList<>();
                String result = new String();
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .get()
                            .url(url)
                            .build();
                    Response response = client.newCall(request).execute();
                    result = response.body().string();
                    Object obj = Class.forName(c.getName()).newInstance();
                    Object json = new JSONTokener(result.toString()).nextValue();
                    if (json instanceof JSONObject) {
                        JSONObject jsonObject = new JSONObject(result.toString());
                        Object newObj = Class.forName(c.getName()).newInstance();
                        Field[] fields = newObj.getClass().getFields();
                        for (Field field : fields) {
                            JSONKey mapJson = field.getAnnotation(JSONKey.class);
                            if (mapJson != null) {
                                field.set(obj, jsonObject.get(mapJson.value()));
                                Field newField = newObj.getClass().getField(field.getName());
                                newField.set(newObj, jsonObject.get(mapJson.value()));
                            }
                            arrayList.add(newObj);
                        }
                    } else if (json instanceof JSONArray) {
                        JSONArray jsonArray = new JSONArray(result.toString());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Object newObj = Class.forName(c.getName()).newInstance();
                            Field[] fields = newObj.getClass().getFields();
                            for (Field field : fields) {
                                JSONKey mapJson = field.getAnnotation(JSONKey.class);
                                if (mapJson != null) {
                                    if (!field.getType().isAssignableFrom(ArrayList.class))
                                        field.set(newObj, jsonObject.get(mapJson.value()));
                                    else {
                                        JSONArray array = jsonObject.getJSONArray(mapJson.value());
                                        ArrayList<Object> arrayList1 = new ArrayList<>();
                                        for (int j = 0; j < array.length(); j++) {
                                            ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
                                            Class<?> generic = (Class<?>) parameterizedType.getActualTypeArguments()[0];
                                            Object object = Class.forName(generic.getName()).newInstance();
                                            Field[] genericFields = object.getClass().getFields();
                                            JSONObject jsonObject1 = array.getJSONObject(j);
                                            for (Field f : genericFields) {
                                                JSONKey m = f.getAnnotation(JSONKey.class);
                                                if (m != null)
                                                    f.set(object, jsonObject1.get(m.value()));
                                            }
                                            arrayList1.add(object);
                                        }
                                        field.set(newObj, arrayList1);
                                    }
                                }
                            }
                            arrayList.add(newObj);
                        }
                    }
                    onJSONLoaded.onLoad(arrayList);
                } catch (Exception e) {
                    try {
                        throw new Exception(e.toString());
                    } catch (Exception ex) {

                    }

                }
            }
        }).start();
    }


}
