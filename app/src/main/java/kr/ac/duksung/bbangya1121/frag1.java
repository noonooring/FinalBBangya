package kr.ac.duksung.bbangya1121;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class frag1 extends Fragment {
    private boolean isFragmentB = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.frag1,container,false);


        ImageView imageView = (ImageView) v.findViewById(R.id.imageView);
        imageView.setClipToOutline(true);

        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new DistanceFragment());
        fragmentTransaction.commit();

        Button switchbutton = v.findViewById(R.id.switch2);
        switchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment();
            }
        });

        return v;
    }
    public void switchFragment() {
        Fragment fr;

        if (isFragmentB) {
            fr = new HotFragment();
        } else {
            fr = new DistanceFragment();
        }

        isFragmentB = !isFragmentB;

        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fr);
        fragmentTransaction.commit();
    }

}


