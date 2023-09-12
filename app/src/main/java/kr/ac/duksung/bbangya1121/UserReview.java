package kr.ac.duksung.bbangya1121;


import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UserReview extends StringRequest {

    //서버 URL 설정(php 파일 연동)
    final static private String URL = "http://jkbest0901.dothome.co.kr/review.php";
    private Map<String, String> map;

    public UserReview(String UserTitle, String UserReview, String UserRate, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("UserTitle", UserTitle);
        map.put("UserReview", UserReview);
        map.put("UserRate", UserRate);

    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}