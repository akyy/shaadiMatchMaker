package com.shaadi.match.maker.featureModules.landing.views;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.shaadi.match.maker.R;
import com.shaadi.match.maker.application.MatchMakerApplication;
import com.shaadi.match.maker.databinding.ActivityHomeBinding;
import com.shaadi.match.maker.db.tables.MatchingUsersTable;
import com.shaadi.match.maker.featureModules.landing.adapters.HomeActivityRecyclerAdapter;
import com.shaadi.match.maker.featureModules.landing.di.DaggerHomeActivityComponent;
import com.shaadi.match.maker.featureModules.landing.di.HomeActivityComponent;
import com.shaadi.match.maker.featureModules.login.LoginActivity;
import com.shaadi.match.maker.preferences.CommonPreferences;
import com.shaadi.match.maker.utils.permissionManager.PermissionResult;
import com.shaadi.match.maker.utils.permissionManager.PermissionUtils;
import com.shaadi.match.maker.utils.Util;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by ajay
 */
public class HomeActivity extends AppCompatActivity {


    @Inject
    Util util;

    @Inject
    CommonPreferences prefs;
    
    @Inject
    PermissionUtils permissionUtils;

    private ActivityHomeBinding binding;
    private HomeActivityRecyclerAdapter homeActivityRecyclerAdapter;
    private HomeActivityComponent homeActivityComponent;
    private HomeActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        viewModel = ViewModelProviders.of(this).get(HomeActivityViewModel.class);

        homeActivityComponent = DaggerHomeActivityComponent.builder()
                .applicationComponent(((MatchMakerApplication) getApplication()).getApplicationComponent())
                .build();
        homeActivityComponent.inject(this);
        homeActivityComponent.inject(viewModel);
        
        permissionUtils.setActivityContext(this);

        binding.toolbar.setTitle("Home");
        binding.toolbar.setTitleTextAppearance(this, R.style.WhiteToolBarTitleMedium);
        setSupportActionBar(binding.toolbar);

        prefs.setFirstTimeLogin(false);

        util.showLoadingDialog(this);

        setAdapter();
        setObservers();
        setClickListeners();
        callAllMatchesAPI(10);
        checkPermissions();

    }
    
    private void checkPermissions() {

        boolean isPermissionsGranted = permissionUtils.isMultiplePermissionsGranted(HomeActivity.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA});

        if(isPermissionsGranted){

        }else {
            askPermissions();
        }

    }
    
     // Need to use if using Permission util
    private void askPermissions() {

        permissionUtils.askCompactMultiplePermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, new PermissionResult() {
            @Override
            public void permissionGranted() {
            }

            @Override
            public void permissionDenied() {

            }
            @Override
            public void permissionForeverDenied() {
                //permissionUtils.openSettingsApp(HomeActivity.this);
            }
        });

    }
    
    private void callAllMatchesAPI(int count){
        viewModel.getAllMatches(count);
    }

    private void callAllMatchesfromDB(){
        viewModel.getDataFromDb();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.more:
                logOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
     // Need to set this if using Permission util
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionUtils.checkRequestedPermissionResult(requestCode, permissions, grantResults);
    }

    private void logOut() {

        prefs.setFirstTimeLogin(true);

        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        startActivity(intent);
        finish();

    }

    private void setClickListeners() {

        binding.pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callAllMatchesAPI(10);
            }
        });

    }

    private void setObservers() {

        allMatchesDataSuccess();
        allMatchesDataError();
        getDbData();

    }

    private void allMatchesDataError() {

        viewModel.getAllMatchesDataError().observe(this, new Observer<Throwable>() {
            @Override
            public void onChanged(@Nullable Throwable throwable) {
                Toast.makeText(HomeActivity.this, "Something went wrong. Please check you internet connectivity\n We are showing offline data", Toast.LENGTH_SHORT).show();
                binding.txtNoData.setVisibility(View.VISIBLE);
                binding.recyclerView.setVisibility(View.GONE);
                util.dismissLoadingDialog();
                binding.pullToRefresh.setRefreshing(false);
                callAllMatchesfromDB();
            }
        });

    }

    private void allMatchesDataSuccess() {

        viewModel.getAllMatchesData().observe(this, new Observer<MatchingUsersTable>() {
            @Override
            public void onChanged(@Nullable MatchingUsersTable matchingUsersResponse) {
                binding.txtNoData.setVisibility(View.GONE);
                binding.recyclerView.setVisibility(View.VISIBLE);
                binding.pullToRefresh.setRefreshing(false);
                util.dismissLoadingDialog();
                updateData(matchingUsersResponse);
            }
        });

    }

    private void updateData(MatchingUsersTable matchingUsersResponse) {
        homeActivityRecyclerAdapter.updateData(matchingUsersResponse.getResults());
    }

    private void setAdapter() {

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        homeActivityRecyclerAdapter = new HomeActivityRecyclerAdapter(this);
        homeActivityComponent.inject(homeActivityRecyclerAdapter);
        binding.recyclerView.setAdapter(homeActivityRecyclerAdapter);


    }

    private void getDbData(){
        viewModel.getMatchingUsersTableLiveData().observe(this, new Observer<List<MatchingUsersTable>>() {
            @Override
            public void onChanged(@Nullable List<MatchingUsersTable> matchingUsersTables) {
                if (matchingUsersTables!=null && matchingUsersTables.size()>0) {
                    binding.txtNoData.setVisibility(View.GONE);
                    binding.recyclerView.setVisibility(View.VISIBLE);
                    binding.pullToRefresh.setRefreshing(false);
                    util.dismissLoadingDialog();
                    updateData(matchingUsersTables.get(0));
                }else {
                    binding.txtNoData.setVisibility(View.VISIBLE);
                    binding.recyclerView.setVisibility(View.GONE);
                    binding.txtNoData.setText("No offline data found\nPlease check your internet connectivity");
                    util.dismissLoadingDialog();
                    binding.pullToRefresh.setRefreshing(false);
                }
            }
        });
    }
}
