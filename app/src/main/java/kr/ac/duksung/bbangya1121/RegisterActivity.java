package kr.ac.duksung.bbangya1121;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import kr.ac.duksung.bbangya1121.MainActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText ID, Password;
    Button Signup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        Signup = (Button) findViewById(R.id.signupButton);
        ID = (EditText) findViewById(R.id.signup_idInput);
        Password = (EditText) findViewById(R.id.signup_pwInput);

        //회원가입 버튼 이벤트
        Signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String id = ID.getText().toString();
                String pw = Password.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success)
                            {
                                Toast.makeText(getApplicationContext(), "성공", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(intent);
                            }

                            else
                            {
                                Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }

                        catch(JSONException e)
                        {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "예외 1", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                };

                RegisterRequest signupRequestActivity = new RegisterRequest(id, pw, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(signupRequestActivity);
            }
        });
    }
}