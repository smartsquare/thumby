# thumb-generator
Thumbnail service of the smart-image project

Default HTTP listen port is 8888

# Environment Variables

| Name  | Description |
| ----  | ----------- |
| STORAGE_MODE | Set to 'local-fs' to store images and thumbnails in ./data - any other value for google cloud storage |
| GOOGLE_APPLICATION_CREDENTIALS | Set to location of credentials json file when using cloud storage during local testing |
| BUCKET_NAME | Name of cloud storage bucket to use. Defaults to 'image_upload_fs'.  |