package main

type OffersResponse struct {
	Code        string `json:"code"`
	Count       int    `json:"count"`
	Information struct {
					AppName         string `json:"app_name"`
					Appid           int    `json:"appid"`
					Country         string `json:"country"`
					Language        string `json:"language"`
					SupportURL      string `json:"support_url"`
					VirtualCurrency string `json:"virtual_currency"`
				} `json:"information"`
	Message     string `json:"message"`
	Offers      []struct {
		Link            string `json:"link"`
		OfferID         int    `json:"offer_id"`
		OfferTypes      []struct {
			OfferTypeID int    `json:"offer_type_id"`
			Readable    string `json:"readable"`
		} `json:"offer_types"`
		Payout          int    `json:"payout"`
		RequiredActions string `json:"required_actions"`
		Teaser          string `json:"teaser"`
		Thumbnail       struct {
							Hires  string `json:"hires"`
							Lowres string `json:"lowres"`
						} `json:"thumbnail"`
		TimeToPayout    struct {
							Amount   int    `json:"amount"`
							Readable string `json:"readable"`
						} `json:"time_to_payout"`
		Title           string `json:"title"`
	} `json:"offers"`
	Pages       int `json:"pages"`
}

