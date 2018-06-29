package main;

import "os"
import "path"
import "context"
import "bytes"
import "io"
import "io/ioutil"
import (
    "cloud.google.com/go/storage"
    "fmt"
)

type StorageService interface {
    //Writes the specified bytes into storage and returns the publicly accessible url.
    Store(bucket, filename string, data []byte) (string, error)

    Retrieve(bucket, filename string) ([]byte, error)
}

type LocalFsStorageService struct {}

func (_ LocalFsStorageService) Store(bucket, filename string, data []byte) (string, error) {
    fullname := path.Join("./data", bucket, filename)
    dirname := path.Dir(fullname)
    os.MkdirAll(dirname, 0750);
    err := ioutil.WriteFile(fullname, data, 0640)
    if err != nil {
        return "", err
    }
    return fullname, nil
}

func (_ LocalFsStorageService) Retrieve(bucket, filename string) ([]byte, error) {
    return ioutil.ReadFile(path.Join("./data", bucket, filename))
}

func NewLocalFsStorageService() StorageService {
    os.MkdirAll("./data", 0750);
    return LocalFsStorageService{}
}


type GoogleCloudStorageService struct {
}

func (gcs *GoogleCloudStorageService) Store(bucketName, filename string, data []byte) (string, error) {
    ctx := context.Background()
    client, err := storage.NewClient(ctx)

    if err != nil {
        return "", fmt.Errorf("Failed to create client: %s", err.Error())
    }


    obj := client.Bucket(bucketName).Object(filename)
    w := obj.NewWriter(ctx)
    defer w.Close()

    _, err = io.Copy(w, bytes.NewReader(data))
    if err != nil {
        return "", fmt.Errorf("Failed to write data: %s", err.Error())
    }

    return "https://storage.cloud.google.com/" + bucketName + "/" + filename, nil
}

func (gcs *GoogleCloudStorageService) Retrieve(bucketName, filename string) ([]byte, error) {
    ctx := context.Background()
    client, err := storage.NewClient(ctx)

    if err != nil {
        return nil, fmt.Errorf("Failed to create client: %s", err.Error())
    }

    obj := client.Bucket(bucketName).Object(filename)
    r, err := obj.NewReader(ctx)
    if err != nil {
        return nil, fmt.Errorf("Failed to read data: %s", err.Error())
    }
    defer r.Close()

    var buffer bytes.Buffer

    _, err = io.Copy(&buffer, r)
    if err != nil {
        return nil, fmt.Errorf("Failed to read data: %s", err.Error())
    }

    return buffer.Bytes(), nil
}

func NewGoogleCloudStorageService() StorageService {
    return &GoogleCloudStorageService{}
}