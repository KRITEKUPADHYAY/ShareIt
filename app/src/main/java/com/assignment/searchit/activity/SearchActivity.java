package com.assignment.searchit.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.assignment.searchit.R;
import com.assignment.searchit.adapter.SearchAdapter;
import com.assignment.searchit.dialog.DialogFullImage;
import com.assignment.searchit.dialog.DialogProgress;
import com.assignment.searchit.model.SearchImageResponse;
import com.assignment.searchit.netwrok.APIClient;
import com.assignment.searchit.netwrok.SearchItAPI;
import com.assignment.searchit.util.NetworkConnectivityHelper;
import com.assignment.searchit.util.ToastHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements SearchAdapter.OnSearchResultClicked {

    SearchAdapter searchAdapter;
    RecyclerView rvSearchResult;
    LinearLayoutManager layoutManager;
    ArrayList<SearchImageResponse.ImageDetails> data = new ArrayList<>();
    SearchView svSearchImage;
    LinearLayout llStartSearch;
    DialogProgress dialogProgress;
    NetworkConnectivityHelper networkConnectivityHelper;

    int page = 1;
    int totalHits = 0;
    boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        networkConnectivityHelper = new NetworkConnectivityHelper(this);
        networkConnectivityHelper.startNetworkCallBack();
        setContentView(R.layout.activity_search);

        llStartSearch = findViewById(R.id.llStartSearch);
        rvSearchResult = findViewById(R.id.rvSearchResult);
        svSearchImage = findViewById(R.id.svSearchImage);
        layoutManager = new LinearLayoutManager(this);


        rvSearchResult.setVisibility(View.GONE);
        llStartSearch.setVisibility(View.VISIBLE);
        svSearchImage.setSubmitButtonEnabled(true);

        svSearchImage.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if(NetworkConnectivityHelper.isConnected) {
                    data = new ArrayList<>();
                    page = 1;
                    isFirst = true;
                    if (s.length() >= 3) {
                        svSearchImage.clearFocus();
                        searchImage(page, s);
                    } else {
                        rvSearchResult.setVisibility(View.GONE);
                        llStartSearch.setVisibility(View.VISIBLE);
                    }
                }else{
                    new ToastHelper().makeToast(SearchActivity.this,getString(R.string.no_internet), Toast.LENGTH_LONG);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(NetworkConnectivityHelper.isConnected) {
                    if (s.length() < 3) {
                        data.clear();
                        page = 1;
                        isFirst = true;
                        rvSearchResult.setVisibility(View.GONE);
                        llStartSearch.setVisibility(View.VISIBLE);
                    }
                }else{
                    new ToastHelper().makeToast(SearchActivity.this,getString(R.string.no_internet), Toast.LENGTH_LONG);
                }
                return false;
            }
        });

        rvSearchResult.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                if (!rvSearchResult.canScrollVertically(1)) {
                    if(data.size()<totalHits && !dialogProgress.isShowing()){
                        page++;
                        Log.d("hello","Total : "+totalHits+" "+data.size()+" "+page+" "+svSearchImage.getQuery().toString().trim());
                        searchImage(page, svSearchImage.getQuery().toString().trim());
                    }
                }
            }
        });
    }

    private void searchImage(int page, String searchKey){

        showProgressDialog(this);
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("key", getString(R.string.pixabay_api_key));
        queryMap.put("q", searchKey);
        queryMap.put("image_type","photo");
        queryMap.put("page",String.valueOf(page));

        Log.d("hello","QueryMap : "+queryMap.entrySet());

        Call<SearchImageResponse> call = APIClient.getClient().create(SearchItAPI.class).searchImage(queryMap);
        call.enqueue(new Callback<SearchImageResponse>() {
            @Override
            public void onResponse(Call<SearchImageResponse> call, Response<SearchImageResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().getHits() != null) {
                        data.addAll(response.body().getHits());
                        Log.d("hello","Data Size : "+data.size());
                        if(data.isEmpty()){
                            cancelProgressDialog();
                            new ToastHelper().makeToast(SearchActivity.this,getString(R.string.no_result), Toast.LENGTH_LONG);
                        }else{
                            rvSearchResult.setVisibility(View.VISIBLE);
                            llStartSearch.setVisibility(View.GONE);
                            totalHits = Integer.parseInt(response.body().getTotal());
                            if(isFirst){
                                isFirst = false;
                                searchAdapter = new SearchAdapter(data, SearchActivity.this, SearchActivity.this);
                                rvSearchResult.setAdapter(searchAdapter);
                                rvSearchResult.setLayoutManager(layoutManager);
                            }else{
                                searchAdapter.notifyDataSetChanged();
                            }
                        }
                    }else{
                        new ToastHelper().makeToast(SearchActivity.this,getString(R.string.error), Toast.LENGTH_LONG);
                    }
                    cancelProgressDialog();
                }else{
                    cancelProgressDialog();
                    new ToastHelper().makeToast(SearchActivity.this,getString(R.string.error), Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailure(Call<SearchImageResponse> call, Throwable t) {
                cancelProgressDialog();
                new ToastHelper().makeToast(SearchActivity.this,getString(R.string.error), Toast.LENGTH_LONG);
            }
        });
    }

    private void showProgressDialog(Context context){
        if(dialogProgress == null){
            dialogProgress = new DialogProgress(context);
            dialogProgress.show();
        }else if(!dialogProgress.isShowing()){
            dialogProgress.show();
        }else{
            cancelProgressDialog();
            dialogProgress.show();
        }
    }

    private void cancelProgressDialog(){
        if(dialogProgress != null){
            dialogProgress.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(networkConnectivityHelper != null){
            networkConnectivityHelper.stopNetworkCallBack();
        }
    }

    @Override
    public void onOpenInBrowser(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    @Override
    public void onOpenInFullScreen(String url) {
        Bundle bundle = new Bundle();
        bundle.putString("url",url);
        DialogFragment dialogFragment = new DialogFullImage();
        dialogFragment.setArguments(bundle);
        dialogFragment.setCancelable(false);
        dialogFragment.show(getSupportFragmentManager(),"Full Screen Image");
    }
}