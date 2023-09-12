package kr.ac.duksung.bbangya1121.ui.slideshow;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import kr.ac.duksung.bbangya1121.LoginActivity;
import kr.ac.duksung.bbangya1121.MainActivity;
import kr.ac.duksung.bbangya1121.R;
import kr.ac.duksung.bbangya1121.ReviewActivity;
import kr.ac.duksung.bbangya1121.UserReview;
import kr.ac.duksung.bbangya1121.databinding.FragmentSlideshowBinding;
import kr.ac.duksung.bbangya1121.ui.home.HomeFragment;
import kr.ac.duksung.bbangya1121.ui.slideshow.SlideshowViewModel;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;

    private EditText review_title, review_text;
    private float review_rate;

    private Button check_button, delete_button, browse;
    private AlertDialog dialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View v = binding.getRoot();

        review_title = v.findViewById(R.id.titleEdit);
        RatingBar reviewRating = (RatingBar) v.findViewById(R.id.reviewRating);
        review_text = v.findViewById(R.id.reviewEdit);

        reviewRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                review_rate = rating;
            }
        });
        check_button = v.findViewById(R.id.okButton);
        check_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String UserTitle = review_title.getText().toString();
                final String UserReview = review_text.getText().toString();
                final String UserRate = String.valueOf(review_rate);

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                dialog = builder.setMessage("리뷰 저장을 완료했습니다.").create();
                                dialog.show();
                                return;
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                queue.add(registerRequest);
            }
        });

        delete_button = v.findViewById(R.id.cancelButton);
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Intent = new Intent(getActivity(), MainActivity.class);
                startActivity(Intent);
            }
        });

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}