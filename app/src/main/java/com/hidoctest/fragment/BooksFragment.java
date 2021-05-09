package com.hidoctest.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hidoctest.R;
import com.hidoctest.adapter.BookListAdapter;
import com.hidoctest.model.Book;
import com.hidoctest.model.BookList;
import com.hidoctest.util.NetworkManager;
import com.hidoctest.viewmodel.BookViewModel;

import java.util.ArrayList;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BooksFragment extends Fragment {
    @BindView(R.id.edtSearchbook)
    EditText edtSearchbook;
    @BindView(R.id.search_btn)
    ImageButton search_btn;
    @BindView(R.id.booksRecyclerList)
    RecyclerView booksRecyclerList;
    @BindView(R.id.emptyView)
    LinearLayout emptyView;
    @BindView(R.id.noConnectionView)
    LinearLayout noConnectionView;
    @BindView(R.id.loading_indicator)
    ProgressBar progressBar;
    @BindView(R.id.try_again_imageview)
    ImageView try_again_imageview;
    private ArrayList<Book> bookList;
    private BookListAdapter bookListAdapter;
    BookViewModel bookViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_fragment, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        bookList = new ArrayList<>();
        bookListAdapter = new BookListAdapter(getContext(), bookList);
        bookViewModel = ViewModelProviders.of(this).get(BookViewModel.class);
        LinearLayoutManager verticalLayoutManagaer = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        booksRecyclerList.setLayoutManager(verticalLayoutManagaer);
        booksRecyclerList.setAdapter(bookListAdapter);
        if (!NetworkManager.isConnected(getContext())) {
            noConnectionView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }else {
            noConnectionView.setVisibility(View.GONE);
            loadBooksData();
        }


    }

    @OnClick({R.id.search_btn,R.id.try_again_imageview})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_btn:
                serachBooksData(edtSearchbook.getText().toString());
                break;
            case R.id.try_again_imageview:

                if (!NetworkManager.isConnected(getContext())) {
                    noConnectionView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }else {
                    noConnectionView.setVisibility(View.GONE);
                    loadBooksData();
                }
                break;
        }
    }

    private void serachBooksData(String searchText) {
        noConnectionView.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        if(searchText.equals("")){
            bookViewModel.getBooksData();
        }else {
            bookViewModel.getSearchBooksData(searchText);
        }
    }

    private void loadBooksData() {

        bookViewModel.getBooksData();
        bookViewModel.error().observe((LifecycleOwner) getContext(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean) {
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
        bookViewModel.isLoading().observe((LifecycleOwner) getContext(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.GONE);
                }

            }
        });
        bookViewModel.bookDataReceived().observe((LifecycleOwner) getContext(), new Observer<BookList>() {
            @Override
            public void onChanged(BookList booksList) {
                bookList.clear();
                if(booksList.getItems()!=null) {
                    if (booksList.getItems().size() > 0) {
                        bookList.addAll(booksList.getItems());
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
                bookListAdapter.notifyDataSetChanged();
            }
        });

    }

}
