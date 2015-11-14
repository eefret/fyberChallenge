package main
import (
	"net/http"
	"net/url"
	"sort"
	"fmt"
	"crypto/sha1"
	"io"
)

const API_KEY = "e95a21621a1865bcbae3bee89c4d4f84"
const okResponse string = `
{
  "code": "OK",
  "message": "OK",
  "count": 1,
  "pages": 1,
  "information": {
    "app_name": "SP Test App",
    "appid": 157,
    "virtual_currency": "Coins",
    "country": " US",
    "language": " EN",
    "support_url": "http://iframe.fyber.com/mobile/DE/157/my_offers"
  },
  "offers": [
    {
      "title": "Tap  Fish",
      "offer_id": 13554,
      "teaser": "Download and START",
      "required_actions": "Download and START",
      "link": "http://iframe.fyber.com/mbrowser?appid=157&lpid=11387&uid=player1",
      "offer_types": [
        {
          "offer_type_id": 101,
          "readable": "Download"
        },
        {
          "offer_type_id": 112,
          "readable": "Free"
        }
      ],
      "thumbnail": {
        "lowres": "http://cdn.fyber.com/assets/1808/icon175x175-2_square_60.png",
        "hires": "http://cdn.fyber.com/assets/1808/icon175x175-2_square_175.png"
      },
      "payout": 90,
      "time_to_payout": {
        "amount": 1800,
        "readable": "30 minutes"
      }
    }
  ]
}`
const errResponse string = `
{
  "code": "ERROR",
  "message": "ERROR",
  "count": 0,
  "pages": 0,
  "information": {
    "app_name": "spiderman",
    appid": 0,
    "virtual_currency": "",
    "country": "",
    "language": " ",
    "support_url": ""
  },
  "offers": [

  ]
}`

func offersHandler(w http.ResponseWriter, r *http.Request) {
	fmt.Println("got request")
	params := r.URL.Query()
	hashKey := params.Get("hashkey")
	params.Del("hashkey")
	generatedHashKey := generateHash(params, API_KEY)
	if hashKey == generatedHashKey{
		w.Header().Add("X-Sponsorpay-Response-Signature", generateHash(params, API_KEY))
		io.WriteString(w, okResponse)
		fmt.Println("success")
	}else{
		io.WriteString(w, errResponse)
		fmt.Println("error")
	}
}

func allHandler(w http.ResponseWriter, r *http.Request) {
	io.WriteString(w, "OK")
}

func main() {
	http.HandleFunc("/", allHandler)
	http.HandleFunc("/feed/v1/offers.json", offersHandler)
	http.ListenAndServe(":8080", nil)
}

func generateHash(params url.Values, apiKey string) string{
	var keys []string
	for k:= range params {
		keys = append(keys, k)
	}
	sort.Strings(keys)

	var paramsString string
	for _, k := range keys{
		paramsString += k
		paramsString += "="
		paramsString += params.Get(k)
		paramsString += "&"
	}
	paramsString += apiKey
	return fmt.Sprintf("%x", sha1.Sum([]byte(paramsString)))
}




