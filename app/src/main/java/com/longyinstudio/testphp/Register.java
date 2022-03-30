package com.longyinstudio.testphp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class Register extends AppCompatActivity {

    private final String registerUrl = "https://www.longyinstudio.cn/php/register.php";
    private String result;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        EditText edit_user = findViewById(R.id.edit_user);
        EditText edit_password = findViewById(R.id.edit_password);
        TextView txt_return = findViewById(R.id.txt_return);
        Button btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(view ->
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        OkHttpUtils
                                .post()
                                .url(registerUrl)
                                .addParams("user", edit_user.getText().toString())
                                .addParams("password", edit_password.getText().toString())
                                .build()
                                .execute(new Callback() {
                                    @Override
                                    public Object parseNetworkResponse(Response response, int id) throws IOException, JSONException {
                                        result = response.body().string().trim();
                                        Log.e("cs1", result);
                                        runOnUiThread(() ->
                                                txt_return.setText(result)
                                        );
                                        JSONObject jsonObject = new JSONObject(result);
                                        int code = jsonObject.getInt("code");
                                        String msg = jsonObject.getString("msg");
                                        if (code == 200) {
                                            Log.e("cs", jsonObject.getString("msg"));
                                            handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(Register.this, msg, Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                            Intent intent = new Intent(Register.this, Home.class);
                                            startActivity(intent);
                                        } else {
                                            Log.e("cs", jsonObject.getString("msg"));
                                            handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(Register.this, msg, Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                        }
                                        return null;
                                    }

                                    @Override
                                    public void onError(Call call, Exception e, int id) {
                                        Log.e("cs2", e.getMessage());
                                        runOnUiThread(() -> txt_return.setText(e.getMessage()));
                                    }

                                    @Override
                                    public void onResponse(Object response, int id) {
                                    }
                                });
                    }
                }.start()
        );
    }
}