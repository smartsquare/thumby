FROM golang:1.10 as builder

WORKDIR /go/src/app

#Get (most?) dependencies in seperate layer. Leads to larger images, but at least we can cache the results...
RUN go get -v github.com/nfnt/resize github.com/gin-gonic/gin cloud.google.com/go/storage

COPY . .

RUN go get -d -v ./... && \
 go install -v ./... && \
 rm -rf /go/src && \
 rm -rf /go/pkg

FROM golang:1.10 as runtime

COPY --from=builder /go/bin/app /go/bin/app

EXPOSE 8888

CMD ["/go/bin/app"]