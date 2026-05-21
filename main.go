package main

import (
	"net/http"
	"time"

	"github.com/gin-gonic/gin"
	ginprometheus "github.com/zsais/go-gin-prometheus"
)

type PostData struct {
	Username string `json:"username" binding:"required"`
	Password string `json:"password" binding:"required"`
}

func main() {
	router := gin.Default()

	user := "Pira"
	pass := "s3cr3t_p@ss"

	p := ginprometheus.NewPrometheus("gin")
	p.Use(router)

	router.POST("/user/login", func(ctx *gin.Context) {
		var json PostData

		if err := ctx.ShouldBindJSON(&json); err != nil {
			ctx.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
			return
		}

		if json.Username == user && json.Password == pass {
			ctx.JSON(http.StatusOK, gin.H{"message": "Acesso permitido"})
			return
		}

		ctx.JSON(http.StatusUnauthorized, gin.H{"message": "Acesso negado"})
	})

	server := &http.Server{
		Addr:         ":8080",
		Handler:      router,
		ReadTimeout:  10 * time.Second,
		WriteTimeout: 10 * time.Second,
		IdleTimeout:  30 * time.Second,
	}

	server.ListenAndServe()
}
