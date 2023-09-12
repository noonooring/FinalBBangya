package kr.ac.duksung.bbangya1121.ui.home;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.minew.beacon.BeaconValueIndex;
import com.minew.beacon.BluetoothState;
import com.minew.beacon.MinewBeacon;
import com.minew.beacon.MinewBeaconManager;
import com.minew.beacon.MinewBeaconManagerListener;

import java.util.Collections;
import java.util.List;

import kr.ac.duksung.bbangya1121.BeaconListAdapter;
import kr.ac.duksung.bbangya1121.DistanceActivity;
import kr.ac.duksung.bbangya1121.HotActivity;
import kr.ac.duksung.bbangya1121.MainActivity;
import kr.ac.duksung.bbangya1121.MapActivity;
import kr.ac.duksung.bbangya1121.R;
import kr.ac.duksung.bbangya1121.RecycleViewDivider;
import kr.ac.duksung.bbangya1121.UserRssi;
import kr.ac.duksung.bbangya1121.ViewPagerAdapter;
import kr.ac.duksung.bbangya1121.databinding.FragmentHomeBinding;
import kr.ac.duksung.bbangya1121.ui.slideshow.SlideshowFragment;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;

    private static final int REQUEST_ACCESS_FINE_LOCATION = 1000;
    private MinewBeaconManager mMinewBeaconManager;
    private RecyclerView mRecycle;
    private BeaconListAdapter mAdapter;
    private static final int REQUEST_ENABLE_BT = 2;
    private boolean isScanning;

    UserRssi comp = new UserRssi();
    private FloatingActionButton mStart_scan;
    private boolean mIsRefreshing;
    private int state;
    private FragmentActivity mContext;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        ImageView image = (ImageView) root.findViewById(R.id.imageView);
//        image.setClipToOutline(true);
//
//        Button button2 = (Button) root.findViewById(R.id.button2);
//        button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent button2 = new Intent(getActivity(), HotActivity.class);
//                startActivity(button2);
//
//            }
//        });
//        Button button3 = (Button) root.findViewById(R.id.button3);
//        button3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent button3 = new Intent(getActivity(), DistanceActivity.class);
//                startActivity(button3);
//
//            }
//        });
//        Button map_button = (Button) root.findViewById(R.id.mapbutton);
//        map_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent mapbutton = new Intent(getActivity(), MapActivity.class);
//                startActivity(mapbutton);
//            }
//        });
//
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}