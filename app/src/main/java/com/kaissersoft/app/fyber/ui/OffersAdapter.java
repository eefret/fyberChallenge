package com.kaissersoft.app.fyber.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaissersoft.app.fyber.R;
import com.kaissersoft.app.fyber.net.response.OfferListResponse;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by eefret on 14/11/15.
 */
public class OffersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context c;
    ArrayList<OfferListResponse.Offer> offers;

    public OffersAdapter(Context c, ArrayList<OfferListResponse.Offer> offers) {
        this.c = c;
        this.offers = offers;
    }

    /**
     * Adds or replaces offers in the dataset
     *
     * @param offers ArrayList<Offer>
     */
    public void addOffers(ArrayList<OfferListResponse.Offer> offers) {
        for (OfferListResponse.Offer offer : offers) {
            if (this.offers.indexOf(offer) > -1) {
                this.offers.set(this.offers.indexOf(offer), offer);
            } else {
                this.offers.add(offer);
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OfferViewHolder(LayoutInflater.from(c).inflate(R.layout.item_offer, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        OfferViewHolder viewHolder = (OfferViewHolder) holder;
        OfferListResponse.Offer offer = offers.get(position);

        viewHolder.tvTitle.setText(offer.title);
        viewHolder.tvPayout.setText(offer.payout);
        viewHolder.tvTeaser.setText(offer.teaser);
        ImageLoader.getInstance().displayImage(offer.thumbnail.highres, viewHolder.ivThumbnail);

    }

    @Override
    public int getItemCount() {
        return offers.size();
    }


    public static class OfferViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivThumbnail;
        public TextView tvTitle;
        public TextView tvTeaser;
        public TextView tvPayout;

        public OfferViewHolder(View itemView) {
            super(itemView);
            ivThumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            tvTitle = (TextView) itemView.findViewById(R.id.title);
            tvTeaser = (TextView) itemView.findViewById(R.id.teaser);
            tvPayout = (TextView) itemView.findViewById(R.id.payout);
        }
    }
}
