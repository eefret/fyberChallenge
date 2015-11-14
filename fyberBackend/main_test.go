package main
import (
	"encoding/json"
	"bytes"
	"testing"
	"net/url"
	"github.com/stretchr/testify/assert"
	"net/http"
	"net/http/httptest"
	"log"
	"fmt"
)

const testURI = "http://api.fyber.com/feed/v1/offers.json"

func mockResponse(err bool) OffersResponse {
	var response OffersResponse
	if err {
		decoder := json.NewDecoder(bytes.NewBufferString(okResponse))
		decoder.Decode(&response)
	} else {
		decoder := json.NewDecoder(bytes.NewBufferString(errResponse))
		decoder.Decode(&response)
	}
	return response
}

func TestGenerateHash(t *testing.T){
	params := url.Values{}
	params.Add("appid", "157")
	params.Add("uid", "player1")
	params.Add("ip", "212.45.111.17")
	params.Add("locale", "de")
	params.Add("device_id", "2b6f0cc904d137be2e1730235f5664094b831186")
	params.Add("ps_time", "1312211903")
	params.Add("pub0", "campaign2")
	params.Add("page", "2")
	params.Add("timestamp", "1312553361")
	generated := generateHash(params, API_KEY)
	assert.Equal(t, "7a2b1604c03d46eec1ecd4a686787b75dd693c4d", generated)
}

func TestOffersEndpoint(t *testing.T){
	params := url.Values{}
	params.Add("appid", "157")
	params.Add("uid", "player1")
	params.Add("ip", "212.45.111.17")
	params.Add("locale", "de")
	params.Add("device_id", "2b6f0cc904d137be2e1730235f5664094b831186")
	params.Add("ps_time", "1312211903")
	params.Add("pub0", "campaign2")
	params.Add("page", "2")
	params.Add("timestamp", "1312553361")
	generated := generateHash(params, API_KEY)
	params.Add("hashkey", generated)
	req, err := http.NewRequest("GET", testURI+"?"+params.Encode(), nil)
	if err != nil {
		log.Fatal(err)
	}
	w := httptest.NewRecorder()
	offersHandler(w, req)

	fmt.Printf("%d - %s", w.Code, w.Body.String())
	var offerResponse OffersResponse
	decoder := json.NewDecoder(w.Body)
	decoder.Decode(&offerResponse)

	assert.Equal(t, "7a2b1604c03d46eec1ecd4a686787b75dd693c4d", w.HeaderMap.Get("X-Sponsorpay-Response-Signature"))
	assert.Equal(t, "OK", offerResponse.Code)
}