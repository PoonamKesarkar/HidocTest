package com.hidoctest.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hidoctest.R;
import com.hidoctest.activity.BookDetailsActivity;
import com.hidoctest.model.Book;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.ViewHolder> {

    private ArrayList<Book> list;
    private Context context;

    public BookListAdapter(Context context, ArrayList<Book> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.book_list_item, parent, false);
        return new ViewHolder(contentView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(list.get(position).getVolumeInfo().getTitle());
        String authors = "";
        if (list.get(position).getVolumeInfo().getAuthors() != null) {

            for (int j = 0; j < list.get(position).getVolumeInfo().getAuthors().size(); j++) {
                if (authors.equals(""))
                    authors = authors + list.get(position).getVolumeInfo().getAuthors().get(j);
                else {
                    authors = authors + "," + list.get(position).getVolumeInfo().getAuthors().get(j);
                }
            }

        } else {
            authors = "Unknown";
        }
        if (list.get(position).getVolumeInfo().getAverageRating() != null) {
            holder.book_rating.setRating(Float.parseFloat(list.get(position).getVolumeInfo().getAverageRating()));
        } else {
            holder.book_rating.setRating(0);
        }
        holder.authors.setText(authors);
        holder.publishingDate.setText(list.get(position).getVolumeInfo().getPublishedDate());
        if(list.get(position).getVolumeInfo().getImageLinks()!=null) {
            if (list.get(position).getVolumeInfo().getImageLinks().getThumbnail() != null) {
                String imgProtocol = list.get(position).getVolumeInfo().getImageLinks().getThumbnail().replace("http", "https");
                Glide.with(context)
                        .load(imgProtocol)
                        .placeholder(R.drawable.no_books)
                        .into(holder.book_image);
            } else {
                holder.book_image.setImageResource(R.drawable.no_books);
            }
        }else{
            holder.book_image.setImageResource(R.drawable.no_books);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, BookDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("book_data", list.get(position));
                i.putExtras(bundle);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.publishingDate)
        TextView publishingDate;
        @BindView(R.id.book_title)
        TextView title;
        @BindView(R.id.book_authors)
        TextView authors;
        @BindView(R.id.book_rating)
        RatingBar book_rating;
        @BindView(R.id.book_image)
        ImageView book_image;
        @BindView(R.id.cardView)
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
