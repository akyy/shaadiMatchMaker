package com.shaadi.match.maker.featureModules.landing.repo;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.AsyncTask;

import com.shaadi.match.maker.db.MatchMakerDatabase;
import com.shaadi.match.maker.db.tables.MatchingUsersTable;
import com.shaadi.match.maker.featureModules.landing.db.MatchingUsersDao;
import com.shaadi.match.maker.preferences.CommonPreferences;
import com.shaadi.match.maker.utils.Util;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ajay
 */
public class HomeActivityRepository {

    private static HomeActivityRepository instance;


    private HomeActivityRestApi mRestApi;
    private Util mUtil;
    private Context mContext;
    private CommonPreferences mPrefs;
    private MatchMakerDatabase db;


    public static HomeActivityRepository getInstance() {
        if (instance == null) {
            synchronized (HomeActivityRepository.class) {
                if (instance == null) {
                    instance = new HomeActivityRepository();
                }
            }
        }

        return instance;
    }

    public void setVariables(HomeActivityRestApi restApi, Util util, Context context, CommonPreferences prefs, MatchMakerDatabase db) {
        mRestApi = restApi;
        mUtil = util;
        mContext = context;
        mPrefs = prefs;
        this.db = db;
    }

    public void getAllMatches(int count, final MutableLiveData<MatchingUsersTable> allMatchesData, final MutableLiveData<Throwable> allMatchesDataError) {

        mRestApi.getAllMatchesDynamic("" + count).enqueue(new Callback<MatchingUsersTable>() {
            @Override
            public void onResponse(Call<MatchingUsersTable> call, Response<MatchingUsersTable> response) {

                if (response.isSuccessful()) {
                    new insertAsyncTask(db.matchingUsersDao()).execute(response.body());
                    allMatchesData.setValue(response.body());
                } else {
                    allMatchesDataError.setValue(null);
                }

            }

            @Override
            public void onFailure(Call<MatchingUsersTable> call, Throwable t) {
                allMatchesDataError.setValue(t);
            }
        });
    }

    public void getMatchingDataFromDb(MutableLiveData<List<MatchingUsersTable>> matchingUsersTableLiveData) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                matchingUsersTableLiveData.postValue(db.matchingUsersDao().getMatchingUsersData());
            }
        });
    }

    public void getMatchingDataDbCount(MutableLiveData<Integer> dbCount) {
        dbCount.setValue(db.matchingUsersDao().getMatchingUsersDataCount());
    }

    private static class insertAsyncTask extends AsyncTask<MatchingUsersTable, Void, Void> {

        private MatchingUsersDao matchingUsersDao;

        insertAsyncTask(MatchingUsersDao dao) {
            matchingUsersDao = dao;
        }

        @Override
        protected Void doInBackground(MatchingUsersTable... params) {
            matchingUsersDao.insert(params[0]);
            return null;
        }
    }

}
