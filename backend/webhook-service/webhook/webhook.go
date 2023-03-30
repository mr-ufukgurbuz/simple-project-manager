package webhook

import (
	"crypto/hmac"
	"crypto/sha1"
	"encoding/hex"
	"encoding/json"
	"io/ioutil"
	"log"
	"net/http"
)

const secret = "nexuswebhooksecret"

func signBody(secret, body []byte) []byte {
	computed := hmac.New(sha1.New, secret)

	computed.Write(body)

	return []byte(computed.Sum(nil))
}

func verifySignature(secret []byte, signature string, body []byte) bool {
	actual := make([]byte, 20)
	hex.Decode(actual, []byte(signature))

	return hmac.Equal(actual, signBody(secret, body))
}

func Handler(w http.ResponseWriter, r *http.Request) {
	var signature = ""
	if signature = r.Header.Get("x-nexus-webhook-signature"); len(signature) == 0 {
		log.Fatal("No signature")
		return
	}

	body, err := ioutil.ReadAll(r.Body)

	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}

	if !verifySignature([]byte(secret), signature, body) {
		log.Fatal("Invalid signature")
		return
	}

	var result Component
	json.Unmarshal(body, &result)
	updateProject(result)
}
