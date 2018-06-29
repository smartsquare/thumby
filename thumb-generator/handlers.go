package main

import "fmt"
import "image/jpeg"
import "image"
import "strconv"
import "bytes"
import "net/http"
import "github.com/nfnt/resize"
import (
    "github.com/gin-gonic/gin"
    "os"
)

const (
    DEFAULT_IMAGE_BUCKET = "image_upload_fs"
    RESIZE_PATH = "resized"
    THUMBNAIL_PATH = "thumbnails"
)

type Resizer func(width, height uint, image image.Image, interp resize.InterpolationFunction) image.Image;

type Size struct {
                  Width uint `json:"width"`
                  Height uint `json:"height"`
  }

type ResizeResult struct {
    Error string `json:"error"`
    ResultLocation string `json:"resultLocation"`
    Size *Size `json:"size"`
}

func MakeResizeHandler(storage StorageService, bucket string, target_path string, resizer Resizer) func(c *gin.Context) {
    return func(c *gin.Context) {
        filename := c.Param("filename")
        widthString := c.Param("width")
        heightString := c.Param("height")

        width, err := strconv.Atoi(widthString)
        if err != nil || width <= 0 {
            c.JSON(http.StatusOK, ResizeResult{
                        Error: "width must be a positive number",
                        ResultLocation: "",
                        Size: nil,
                    })
            return
        }

        height, err := strconv.Atoi(heightString)
        if err != nil || height <= 0{
            c.JSON(http.StatusOK, ResizeResult{
                                    Error: "height must be a positive number",
                                    ResultLocation: "",
                                    Size: nil,
                                })
            return
        }

        imgbytes, err := storage.Retrieve(bucket, filename);
        if err != nil {
            c.JSON(http.StatusNotFound, ResizeResult{
                Error: "Requested image not found. Cause: " + err.Error(),
                ResultLocation: "",
                Size: nil,
            })
            return
        }

        image, _, err := image.Decode(bytes.NewReader(imgbytes))
        if err != nil {
            c.JSON(http.StatusInternalServerError, ResizeResult{
                Error: "Could not decode image. Cause: " + err.Error(),
            });
        }

        scaled := resizer(uint(width), uint(height), image, resize.Bilinear);

        var buffer bytes.Buffer
        err = jpeg.Encode(&buffer, scaled, nil)
        if err != nil {
            c.JSON(http.StatusInternalServerError, ResizeResult{
                Error: "Could not save image. Cause: " + err.Error(),
            });
        }

        publicUrl, err := storage.Store(bucket, fmt.Sprintf("%s/%s/%dx%d.jpg", target_path, filename, width, height), buffer.Bytes())
        if err != nil {
            c.JSON(http.StatusInternalServerError, ResizeResult{
                Error: "Could not save image. Cause: " + err.Error(),
            });
        }

        c.JSON(http.StatusOK, ResizeResult{
            Error: "",
            ResultLocation: publicUrl,
            Size: &Size {
                Width: uint(width),
                Height: uint(height),
            },
        })
    }
}


func MakeHealthCheckHandler() func(c *gin.Context) {
    return func(c *gin.Context) {
        c.JSON(http.StatusOK, "OK")
    }
}

func NewEngine(storage StorageService) *gin.Engine {
    r := gin.Default()

    var bucket string
    if os.Getenv("BUCKET_NAME") != "" {
        bucket = os.Getenv("BUCKET_NAME")
    } else {
        bucket = DEFAULT_IMAGE_BUCKET
    }

    r.POST("/img/:filename/resize/:width/:height", MakeResizeHandler(storage, bucket, RESIZE_PATH, resize.Resize))
    r.POST("/img/:filename/thumb/:width/:height", MakeResizeHandler(storage, bucket, THUMBNAIL_PATH, resize.Thumbnail))
    r.GET("/", MakeHealthCheckHandler())
    return r
}