package com.english.quizza;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.english.quizza.databinding.ActivityMainBinding;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.firebase.messaging.FirebaseMessaging;

import org.jetbrains.annotations.NotNull;

import me.ibrahimsn.lib.OnItemSelectedListener;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private AdView mAdView;
    NavigationView navmenu;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private final int UPDATE_REQUEST_CODE = 1871;
    private AppUpdateManager mAppUpdateManager;
    private BroadcastReceiver broadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FirebaseMessaging.getInstance().subscribeToTopic("weather")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Done";
                        if (!task.isSuccessful()) {
                            msg = "Failed";
                        }
                    }
                });

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NotNull InitializationStatus initializationStatus) {
            }
        });
       mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        setSupportActionBar(toolbar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        navmenu=findViewById(R.id.navmenu);
        drawerLayout=findViewById(R.id.drawerlayout);
        toggle = new ActionBarDrawerToggle(MainActivity.this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        FragmentTransaction  transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content,new HomeFragment());
        transaction.commit();

        navmenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction  transaction = getSupportFragmentManager().beginTransaction();
                switch (item.getItemId()){
                    case R.id.invite:
                        Toast.makeText(MainActivity.this, "Invite your friend", Toast.LENGTH_SHORT).show();
                        transaction.replace(R.id.content,new InviteFragment());
                        transaction.commit();
                        break;
                    case R.id.help:
                        transaction.replace(R.id.content,new FeedbackFragment());
                        transaction.commit();
                        break;
                    case R.id.setting:
                        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                        return true;
                    case R.id.logout:
                        transaction.replace(R.id.content,new LogoutFragment());
                        transaction.commit();
                        break;
                    case R.id.privacy:
                         transaction.replace(R.id.content, new PrivacyFragment());
                         transaction.commit();

                        break;
                    case R.id.features:
                        transaction.replace(R.id.content, new AboutusFragment());
                        transaction.commit();
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });


          binding.bottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
              @Override
              public boolean onItemSelect(int i) {
                  FragmentTransaction  transaction = getSupportFragmentManager().beginTransaction();
                  switch (i){
                      case 0:
                          transaction.replace(R.id.content,new HomeFragment());
                          transaction.commit();

                          break;
                      case 1:
                          transaction.replace(R.id.content,new LeaderboardFragment());
                          transaction.commit();

                          break;
                      case 2:
                          transaction.replace(R.id.content,new WalletFragment());
                          transaction.commit();

                          break;
                      case 3:
                          transaction.replace(R.id.content,new ProfileFragment());
                          transaction.commit();

                          break;
                  }
                  return false;
              }
          });
          mAppUpdateManager = AppUpdateManagerFactory.create(this);
          mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
              @Override
              public void onSuccess(AppUpdateInfo appUpdateInfo) {
                 if(appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                         && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                     try {
                         mAppUpdateManager.startUpdateFlowForResult(appUpdateInfo,AppUpdateType.IMMEDIATE,MainActivity.this,UPDATE_REQUEST_CODE);
                     } catch (IntentSender.SendIntentException e) {
                         e.printStackTrace();
                     }
                 }
              }
              });
        broadcastReceiver = new Network_broadcast();
        registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    private InstallStateUpdatedListener installStateUpdatedListener = new InstallStateUpdatedListener() {
    @Override
    public void onStateUpdate(@NonNull InstallState installState) {
        if (installState.installStatus() == InstallStatus.DOWNLOADED){
            showCompletedUpdate();
        }
    }
};
    @Override
    protected void onStop()
    {
        super.onStop();
    }
    private void showCompletedUpdate() {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "App is ready", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Install", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAppUpdateManager.completeUpdate();
            }
        });
        snackbar.show();
    }
@Override
protected void onResume() {
    super.onResume();
    mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
        @Override
        public void onSuccess(AppUpdateInfo appUpdateInfo) {
            if(appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                try {
                    mAppUpdateManager.startUpdateFlowForResult(appUpdateInfo,AppUpdateType.IMMEDIATE,MainActivity.this,UPDATE_REQUEST_CODE);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
            }
        }
    });
}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.homemenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

}