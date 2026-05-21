FROM golang:1.26.3-alpine AS builder
WORKDIR /app
COPY . .
RUN go mod tidy
RUN go build -o main .

FROM gcr.io/distroless/static-debian12
COPY --from=builder /app/main /main
CMD ["/main"]