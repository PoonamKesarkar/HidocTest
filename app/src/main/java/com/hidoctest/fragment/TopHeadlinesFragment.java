package com.hidoctest.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hidoctest.R;
import com.hidoctest.adapter.NewsHedlinesAdapter;
import com.hidoctest.model.Article;
import com.hidoctest.model.News;
import com.hidoctest.util.NetworkManager;
import com.hidoctest.viewmodel.NewsViewModel;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TopHeadlinesFragment extends Fragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.emptyView)
    LinearLayout emptyView;
    @BindView(R.id.noConnectionView)
    LinearLayout noConnectionView;
    @BindView(R.id.loading_indicator)
    ProgressBar progressBar;
    @BindView(R.id.try_again_imageview)
    ImageView try_again_imageview;
    private ArrayList<Article> articles;
    private NewsHedlinesAdapter newsAdapter;
    NewsViewModel newsViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_popular_fragment, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        articles = new ArrayList<>();
        newsAdapter = new NewsHedlinesAdapter(getContext(), articles);
        newsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        LinearLayoutManager verticalLayoutManagaer = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(verticalLayoutManagaer);
        recyclerView.setAdapter(newsAdapter);
        if (!NetworkManager.isConnected(getContext())) {
            noConnectionView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }else {
            noConnectionView.setVisibility(View.GONE);
            loadNewsData();
        }
    }
    @OnClick({R.id.try_again_imageview})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.try_again_imageview:
                if (!NetworkManager.isConnected(getContext())) {
                    noConnectionView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }else {
                    noConnectionView.setVisibility(View.GONE);
                    loadNewsData();
                }
                break;
        }
    }
    private void loadNewsData() {

        newsViewModel.getNewsData();
        newsViewModel.error().observe((LifecycleOwner) getContext(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean) {
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
        newsViewModel.isLoading().observe((LifecycleOwner) getContext(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.GONE);
                }

            }
        });
        newsViewModel.newsDataReceived().observe((LifecycleOwner) getContext(), new Observer<News>() {
            @Override
            public void onChanged(News newsList) {
                if (!articles.isEmpty()){
                    articles.clear();
                }
                if(newsList!=null) {
                    if (newsList.getArticle() != null) {
                        articles.addAll(newsList.getArticle());
                       Log.d("NewsFrag","articles:"+articles.size());
                       Log.d("NewsFrag","articles:"+newsList.getArticle().size());
                    } else {
                        if (!NetworkManager.isConnected(getContext())) {
                            noConnectionView.setVisibility(View.VISIBLE);
                            emptyView.setVisibility(View.GONE);
                        } else {
                            noConnectionView.setVisibility(View.GONE);
                            emptyView.setVisibility(View.VISIBLE);
                        }
                    }
                }else{
                    if (!NetworkManager.isConnected(getContext())) {
                        noConnectionView.setVisibility(View.VISIBLE);
                        emptyView.setVisibility(View.GONE);
                    } else {
                        noConnectionView.setVisibility(View.GONE);
                        emptyView.setVisibility(View.VISIBLE);
                    }
                }

                newsAdapter.notifyDataSetChanged();
            }
        });

    }

}
