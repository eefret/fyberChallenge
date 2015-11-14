package com.kaissersoft.app.fyber.net.response;

import java.util.ArrayList;

/**
 * Created by eefret on 12/11/15.
 */
public class OfferListResponse {
    public String code;
    public String message;
    public int count;
    public int pages;
    public Information information;
    public ArrayList<Offer> offers;

    public class Information{
        public String app_name;
        public String appid;
        public String virtual_currency;
        public String country;
        public String language;
        public String support_url;
    }

    public class Offer{
        public String link;
        public String title;
        public String offer_id;
        public String teaser;
        public String required_actions;
        public Thumbnail thumbnail;
        public ArrayList<OfferType> offer_types;
        public String payout;
        public TimeToPayout time_to_payout;
    }

    public class Thumbnail {
        public String lowres;
        public String highres;
    }

    public class OfferType{
        public String offer_type_id;
        public String readable;
    }

    public class TimeToPayout{
        public String amount;
        public String readable;
    }

}
