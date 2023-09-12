package kr.ac.duksung.bbangya1121;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.minew.beacon.BeaconValueIndex;
import com.minew.beacon.BluetoothState;
import com.minew.beacon.MinewBeacon;
import com.minew.beacon.MinewBeaconManager;
import com.minew.beacon.MinewBeaconManagerListener;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import kr.ac.duksung.bbangya1121.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private DrawerLayout drawerLayout;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FragmentAdapter adapter;

    private static final int REQUEST_ACCESS_FINE_LOCATION = 1000;
    private MinewBeaconManager mMinewBeaconManager;
    private RecyclerView mRecycle;
    private BeaconListAdapter mAdapter;
    private static final int REQUEST_ENABLE_BT = 1;
    private boolean isScanning;

    UserRssi comp = new UserRssi();
    private FloatingActionButton mStart_scan;
    private boolean mIsRefreshing;
    private int state;

    String deviceName;
    TextView bakeryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        setSupportActionBar(toolbar);

        tabLayout = findViewById(R.id.layout_tabs);
        viewPager = findViewById(R.id.view_pager);
        adapter = new FragmentAdapter(getSupportFragmentManager(), 1);
        toolbar = findViewById(R.id.toolbar);

        frag1 fragment1 = new frag1();
        frag2 fragment2 = new frag2();
        frag3 fragment3 = new frag3();

        adapter.addFragment(new frag1());
        adapter.addFragment(new frag2());
        adapter.addFragment(new frag3());

        //뷰페이저와 어댑터 연결
        viewPager.setAdapter(adapter);

        //탭과 뷰페이저 연결
        tabLayout.setupWithViewPager(viewPager);

        Objects.requireNonNull(tabLayout.getTabAt(0)).setText("핫한 빵");
        Objects.requireNonNull(tabLayout.getTabAt(1)).setText("베이커리 랭킹");
        Objects.requireNonNull(tabLayout.getTabAt(2)).setText("주변 베이커리 지도");

        bakeryName = (TextView) findViewById(R.id.titleEdit);
        mStart_scan = (FloatingActionButton) findViewById(R.id.heart);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_rank)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        initView();
        initManager();
        checkBluetooth();
        checkLocationPermition();
        initListener();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_button2) {
            Intent settingIntent = new Intent(this, LoginActivity.class);
            startActivity(settingIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment_content_main, fragment);
        fragmentTransaction.commit();
    }


    private void checkLocationPermition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

            if (permissionCheck == PackageManager.PERMISSION_DENIED) {

                // 권한 없음
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_ACCESS_FINE_LOCATION);
            } else {
                // ACCESS_FINE_LOCATION 에 대한 권한이 이미 있음.
            }
        }
        // OS가 Marshmallow 이전일 경우 권한체크를 하지 않는다.
        else {

        }
    }

    /**
     * check Bluetooth state
     */
    private void checkBluetooth() {
        BluetoothState bluetoothState = mMinewBeaconManager.checkBluetoothState();
        switch (bluetoothState) {
            case BluetoothStateNotSupported:
                Toast.makeText(this, "Not Support BLE", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case BluetoothStatePowerOff:
                showBLEDialog();
                break;
            case BluetoothStatePowerOn:
                break;
        }
    }

    private void initView() {

        mRecycle = (RecyclerView) findViewById(R.id.recyeler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecycle.setLayoutManager(layoutManager);
        mAdapter = new BeaconListAdapter();
        mRecycle.setAdapter(mAdapter);
        mRecycle.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager
                .HORIZONTAL));
    }

    private void initManager() {
        mMinewBeaconManager = MinewBeaconManager.getInstance(this);
    }

    private void initListener() {
        mStart_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mMinewBeaconManager != null) {
                    BluetoothState bluetoothState = mMinewBeaconManager.checkBluetoothState();
                    switch (bluetoothState) {
                        case BluetoothStateNotSupported:
                            Toast.makeText(MainActivity.this, "Not Support BLE", Toast.LENGTH_SHORT).show();
                            finish();
                            break;
                        case BluetoothStatePowerOff:
                            showBLEDialog();
                            return;
                        case BluetoothStatePowerOn:
                            break;
                    }
                }
                if (isScanning) {
                    isScanning = false;
                    if (mMinewBeaconManager != null) {
                        mMinewBeaconManager.stopScan();
                    }
                } else {
                    isScanning = true;
                    try {
                        mMinewBeaconManager.startScan();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        mRecycle.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                state = newState;
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        mMinewBeaconManager.setDeviceManagerDelegateListener(new MinewBeaconManagerListener() {
            @Override
            public void onAppearBeacons(List<MinewBeacon> minewBeacons) {


                MinewBeacon nearestBeacon = null;
                double nearestDistance = Double.MAX_VALUE;

                for (MinewBeacon minewBeacon : minewBeacons) {
                    String UUID = minewBeacon.getBeaconValue(BeaconValueIndex.MinewBeaconValueIndex_UUID).getStringValue();
                    deviceName = minewBeacon.getBeaconValue(BeaconValueIndex.MinewBeaconValueIndex_Name).getStringValue();

                    // E2C56DB5-DFFB-48D2-B060-D0F5A71096E0
                    if (deviceName.equals("MBeacon")) {

                        double distance = calculateDistance(minewBeacon.getBeaconValue(BeaconValueIndex.MinewBeaconValueIndex_RSSI).getIntValue());

                        if (distance < nearestDistance) {
                            nearestDistance = distance;
                            nearestBeacon = minewBeacon;
                        }
                    }
                }


                if(nearestBeacon != null) {
                    AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(MainActivity.this);

                    if(nearestBeacon.getBeaconValue(BeaconValueIndex.MinewBeaconValueIndex_Minor).getStringValue().equals("20103")){
                        myAlertBuilder.setTitle("소정 베이커리");
                        myAlertBuilder.setMessage("현재 가장 가까운 거리의 베이커리는" + "\n" +
                                "소정 베이커리 입니다!" + "\n" + "리뷰를 작성하시겠습니까?");
                        myAlertBuilder.setPositiveButton("네", new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog,int which){
                                // OK 버튼을 눌렀을 경우
                                Intent i = new Intent(MainActivity.this, ReviewActivity.class);
                                startActivity(i);
                                mMinewBeaconManager.stopScan();
                            }
                        });
                        myAlertBuilder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Cancle 버튼을 눌렸을 경우
                                Toast.makeText(getApplicationContext(), "취소하였습니다.",
                                        Toast.LENGTH_SHORT).show();
                                initView();
                            }
                        });

                    }

                    if (nearestBeacon.getBeaconValue(BeaconValueIndex.MinewBeaconValueIndex_Minor).getStringValue().equals("31248")) {
                        myAlertBuilder.setTitle("누누 베이커리");
                        myAlertBuilder.setMessage("현재 가장 가까운 거리의 베이커리는" + "\n" +
                                "누누 베이커리 입니다!" + "\n" + "리뷰를 작성하시겠습니까?");
                        myAlertBuilder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // OK 버튼을 눌렀을 경우
                                Intent i = new Intent(MainActivity.this, ReviewActivity.class);
                                startActivity(i);
                              //  mMinewBeaconManager.stopScan();
                            }
                        });
                        myAlertBuilder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Cancle 버튼을 눌렸을 경우
                                Toast.makeText(getApplicationContext(), "취소하였습니다.",
                                        Toast.LENGTH_SHORT).show();
                                initView();
                            }
                        });
                    }

                   if (nearestBeacon.getBeaconValue(BeaconValueIndex.MinewBeaconValueIndex_Minor).getStringValue().equals("31433")) {
                        myAlertBuilder.setTitle("예진 베이커리");
                        myAlertBuilder.setMessage("현재 가장 가까운 거리의 베이커리는" + "\n" +
                                "예진 베이커리 입니다!" + "\n" + "리뷰를 작성하시겠습니까?");
                    }
                    myAlertBuilder.setPositiveButton("네", new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog,int which){
                            // OK 버튼을 눌렀을 경우
                            Intent i = new Intent(MainActivity.this, ReviewActivity.class);
                            startActivity(i);
                            mMinewBeaconManager.stopScan();
                        }
                    });
                    myAlertBuilder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Cancle 버튼을 눌렸을 경우
                            Toast.makeText(getApplicationContext(), "취소하였습니다.",
                                    Toast.LENGTH_SHORT).show();
                            initView();
                        }
                    });
                   /*
                    myAlertBuilder.setTitle("가장 가까운 베이커리");
                    myAlertBuilder.setMessage("UUID: " + nearestBeacon.getBeaconValue(BeaconValueIndex.MinewBeaconValueIndex_UUID).getStringValue() + "\n" +
                            "Major: " + nearestBeacon.getBeaconValue(BeaconValueIndex.MinewBeaconValueIndex_Major).getStringValue() + "\n" +
                            "Minor: " + nearestBeacon.getBeaconValue(BeaconValueIndex.MinewBeaconValueIndex_Minor).getStringValue() + "\n" +
                            "Distance: " + nearestDistance + "m");



                    myAlertBuilder.setPositiveButton("네", new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog,int which){
                            // OK 버튼을 눌렀을 경우
                            Intent i = new Intent(MainActivity.this, ReviewActivity.class);
                            startActivity(i);
                            mMinewBeaconManager.stopScan();
                        }
                    });
                    myAlertBuilder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Cancle 버튼을 눌렸을 경우
                            Toast.makeText(getApplicationContext(), "취소하였습니다.",
                                    Toast.LENGTH_SHORT).show();
                            initView();
                        }
                    });
                */
                    myAlertBuilder.show();
                }

            }

            @Override
            public void onDisappearBeacons(List<MinewBeacon> minewBeacons) {
                for (MinewBeacon minewBeacon : minewBeacons) {
                    String deviceName = minewBeacon.getBeaconValue(BeaconValueIndex.MinewBeaconValueIndex_Name).getStringValue();
                    //Toast.makeText(getApplicationContext(), "빵집이 다시 검색됩니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onRangeBeacons(final List<MinewBeacon> minewBeacons) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Collections.sort(minewBeacons, comp);
                        Log.e("tag", state + "");
                        if (state == 1 || state == 2) {
                        } else {
                            mAdapter.setItems(minewBeacons);
                        }

                    }
                });
            }

            @Override
            public void onUpdateState(BluetoothState state) {
                switch (state) {
                    case BluetoothStatePowerOn:
                        Toast.makeText(getApplicationContext(), "BluetoothStatePowerOn", Toast.LENGTH_SHORT).show();
                        break;
                    case BluetoothStatePowerOff:
                        Toast.makeText(getApplicationContext(), "BluetoothStatePowerOff", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //stop scan
        if (isScanning) {
            mMinewBeaconManager.stopScan();
        }
    }

    private void showBLEDialog() {
        Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                break;
        }
    }

    private double calculateDistance(int rssi) {
        int txPower = -59; // 실제 비콘의 송신 전력에 따라 수정해야 함
        double ratio = rssi * 1.0 / txPower;

        if (ratio < 1.0) {
            return Math.pow(ratio, 10);
        } else {
            return (0.89976) * Math.pow(ratio, 7.7095) + 0.111;
        }
    }
}
