package kr.ac.duksung.bbangya1121;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import kr.ac.duksung.bbangya1121.MainActivity;
import kr.ac.duksung.bbangya1121.UserReview;


public class ReviewActivity extends AppCompatActivity {

    private EditText review_title, review_text;
    private float review_rate;

    private Button check_button, delete_button, browse;
    private AlertDialog dialog;

    private final int GET_GALLERY_IMAGE = 200;
    private ImageView reviewImage;

    ImageView img;
    Bitmap bitmap;
    String encodeImageString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        review_title = findViewById(R.id.titleEdit);
        RatingBar reviewRating = (RatingBar) findViewById(R.id.reviewRating);
        review_text = findViewById(R.id.reviewEdit);

        reviewRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                review_rate = rating;
            }
        });
/*
        browse = findViewById(R.id.browse);
        browse.setOnClickListener(view -> {
            Dexter.withActivity(ReviewActivity.this)
                    .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse response) {
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType("image/*");
                            startActivityForResult(Intent.createChooser(intent, "Browse Image"), 1);
                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse response) {

                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                            token.continuePermissionRequest();
                        }
                    }).check();
        });
 */
        check_button = findViewById(R.id.checkButton);
        check_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String UserTitle = review_title.getText().toString();
                final String UserReview = review_text.getText().toString();
                final String UserRate = Float.toString(review_rate);
//                final String UserImage = encodeImageString;

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ReviewActivity.this);
                                dialog = builder.setMessage("리뷰 저장을 완료했습니다.").create();
                                dialog.show();
                                Intent intent = new Intent(ReviewActivity.this, MainActivity.class);
                                startActivity(intent);
                                return;
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ReviewActivity.this);
                                dialog = builder.setMessage("리뷰 저장에 실패했습니다.").create();
                                dialog.show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                //서버로 Volley를 이용해서 요청
                kr.ac.duksung.bbangya1121.UserReview registerRequest = new UserReview(UserTitle, UserReview, UserRate, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ReviewActivity.this);
                queue.add(registerRequest);
            }
        });

        delete_button = findViewById(R.id.cancelButton);
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReviewActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Uri filePath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(bitmap);
                encodeBitmapImage(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void encodeBitmapImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] bytesOfImage = byteArrayOutputStream.toByteArray();
        encodeImageString = android.util.Base64.encodeToString(bytesOfImage, Base64.DEFAULT);
    }
}