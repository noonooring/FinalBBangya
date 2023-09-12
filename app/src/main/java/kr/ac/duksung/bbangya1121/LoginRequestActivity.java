package kr.ac.duksung.bbangya1121;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequestActivity extends StringRequest {

    final static private String URL = "http://3.143.112.163/login_test.php";
    private Map<String, String> map;

    public LoginRequestActivity(String ID, String Password, Response.Listener<String> listener)
    {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("ID", ID);
        map.put("Password", Password);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}