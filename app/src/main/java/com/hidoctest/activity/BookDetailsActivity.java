package com.hidoctest.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.hidoctest.R;
import com.hidoctest.model.Book;

public class BookDetailsActivity extends AppCompatActivity {
    Book bookItem;
    @BindView(R.id.app_bar)
    AppBarLayout app_bar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.book_toolbar)
    Toolbar book_toolbar;
    @BindView(R.id.detail_book_image)
    ImageView detail_book_image;
    @BindView(R.id.book_price)
    TextView book_price;
    @BindView(R.id.detail_book_title)
    TextView detail_book_title;
    @BindView(R.id.detail_book_categories)
    TextView detail_book_categories;
    @BindView(R.id.detail_book_rating)
    RatingBar detail_book_rating;
    @BindView(R.id.detail_book_authors)
    TextView detail_book_authors;
    @BindView(R.id.buy_book)
    TextView buy_book;
    @BindView(R.id.preview_book)
    TextView preview_book;
    @BindView(R.id.book_description)
    TextView book_description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        bookItem = (Book) bundle.getSerializable("book_data");

        collapsingToolbarLayout.setTitleEnabled(true);
        collapsingToolbarLayout.setTitle(bookItem.getVolumeInfo().getTitle());
        if(bookItem.getVolumeInfo().getImageLinks()!=null) {
            if (bookItem.getVolumeInfo().getImageLinks().getThumbnail() != null) {
                String imgProtocol = bookItem.getVolumeInfo().getImageLinks().getThumbnail().replace("http", "https");
                Glide.with(this)
                        .load(imgProtocol)
                        .placeholder(R.drawable.no_books)
                        .into(detail_book_image);
            } else {
                detail_book_image.setImageResource(R.drawable.no_books);
            }
        }else{
            detail_book_image.setImageResource(R.drawable.no_books);
        }
        detail_book_title.setText(bookItem.getVolumeInfo().getTitle());
        String authors = "";
        if (bookItem.getVolumeInfo().getAuthors() != null) {

            for (int j = 0; j < bookItem.getVolumeInfo().getAuthors().size(); j++) {
                if (authors.equals(""))
                    authors = authors + bookItem.getVolumeInfo().getAuthors().get(j);
                else {
                    authors = authors + "," + bookItem.getVolumeInfo().getAuthors().get(j);
                }
            }

        } else {
            authors = "Unknown";
        }
        if (bookItem.getVolumeInfo().getAverageRating() != null) {
            detail_book_rating.setRating(Float.parseFloat(bookItem.getVolumeInfo().getAverageRating()));
        } else {
            detail_book_rating.setRating(0);
        }
        detail_book_authors.setText(authors);
        book_description.setText(bookItem.getVolumeInfo().getDescription());
        String categories = "";
        if (bookItem.getVolumeInfo().getCategories() != null) {

            for (int j = 0; j < bookItem.getVolumeInfo().getCategories().size(); j++) {
                if (categories.equals(""))
                    categories = categories + bookItem.getVolumeInfo().getCategories().get(j);
                else {
                    categories = categories + "," + bookItem.getVolumeInfo().getCategories().get(j);
                }
            }

        } else {
            categories = "No Category";
        }
        detail_book_categories.setText(categories);

        String price = "";
        String buyLink = "";
        if (bookItem.getSaleInfo().getSaleability() != null) {

            if (bookItem.getSaleInfo().getSaleability().equals("FOR_SALE")) {
                if (bookItem.getSaleInfo().getListPrice() != null) {
                    price = Double.toString((bookItem.getSaleInfo().getListPrice().getAmount()));
                    price += bookItem.getSaleInfo().getListPrice().getCurrencyCode();
                }

            } else {
                price = "Free";
            }

            book_price.setText(price);

        }
        if (bookItem.getSaleInfo().getBuyLink() != null) {
            buyLink = bookItem.getSaleInfo().getBuyLink();
        }
        String previewLink = "";
        if (bookItem.getVolumeInfo().getPreviewLink() != null) {
            previewLink = bookItem.getVolumeInfo().getPreviewLink();

        } else {
            previewLink = " ";
        }

        if (price.equals("Free")) {
            buy_book.setVisibility(View.INVISIBLE);

        } else {
            buy_book.setVisibility(View.VISIBLE);
        }

        String finalBuyLink = buyLink;
        buy_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(finalBuyLink));
                startActivity(i);
            }

        });
        String finalPreviewLink = previewLink;
        preview_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!finalPreviewLink.isEmpty() || !finalPreviewLink.equals(" ")) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(finalPreviewLink));
                    startActivity(i);
                } else {
                    Toast.makeText(BookDetailsActivity.this, "PreviewLink Not Available", Toast.LENGTH_LONG).show();
                }

            }
        });


    }
}