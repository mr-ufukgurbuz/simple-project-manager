package webhook

import (
	"bytes"
	"encoding/json"
	"io/ioutil"
	"log"
	"net/http"
	"os"
	"time"
)

func updateProject(component Component) {

	if component.Action == "CREATED" {
		log.Println(component.Component.Name)
		payload, err := json.Marshal(map[string]interface{}{
			"status":     2, // SUCCESS
			"uploadDate": generateTimestamp(),
			"assetId":    component.Component.Id,
			"assetName":  component.Component.Name,
		})
		if err != nil {
			log.Fatal(err)
		}

		url := os.Getenv("NEXUS_SERVICE")

		req, err := http.NewRequest(http.MethodPut, url, bytes.NewBuffer(payload))
		req.Header.Set("Content-Type", "application/json")
		if err != nil {
			log.Fatal(err)
		}

		res, err := http.DefaultClient.Do(req)
		if err != nil {
			log.Fatal(err)
		}

		defer res.Body.Close()

		body, err := ioutil.ReadAll(res.Body)
		if err != nil {
			log.Fatal(err)
		}

		log.Printf("Status Code %d", res.StatusCode)
		log.Println(string(body))
	}
}

func generateTimestamp() int64 {
	return int64(time.Nanosecond) * time.Now().UnixNano() / int64(time.Millisecond)
}
