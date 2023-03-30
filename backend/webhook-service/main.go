package main

import (
	"fmt"
	"log"
	"net/http"

	"github.com/joho/godotenv"
	"glgitlab01.company.com.tr/project-package/inhouse/simple-project-manager/backend/webhook-service/webhook"
)

func setupHttpServer() {
	http.HandleFunc("/webhook", webhook.Handler)

	err := http.ListenAndServe(":8080", nil)
	if err != nil {
		log.Fatal(err)
	}
}

func loadEnvironmentVariables() {
	err := godotenv.Load()
	if err != nil {
		log.Fatal(err)
	}
}

func main() {
	fmt.Println("WebHook server started")
	loadEnvironmentVariables()
	setupHttpServer()
}
