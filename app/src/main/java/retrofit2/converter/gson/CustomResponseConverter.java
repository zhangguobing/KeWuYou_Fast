package retrofit2.converter.gson;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.weiwoju.kewuyou.network.HttpStatus;
import com.weiwoju.kewuyou.network.ResponseError;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;


public class CustomResponseConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    CustomResponseConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        HttpStatus httpStatus = gson.fromJson(response, HttpStatus.class);
        if (httpStatus.isCodeInvalid()) {
            value.close();
            throw new ResponseError(httpStatus.getErrcode(), httpStatus.getErrmsg());
        }

//        JsonReader jsonReader = gson.newJsonReader(value.charStream());
//        try {
//            return adapter.read(jsonReader);
//        } finally {
//            value.close();
//        }
        try {
            return adapter.fromJson(response);
        }finally {
            value.close();
        }

    }
}
