package ex.com.bakingapp.data.api;

public class StepApi {
        private int id;
        private String shortDescription;
        private String description;
        private String videoURL;
        private String thumbnailURL;

        public Integer getId() {return id;}
        public void setId(Integer id) {this.id = id;}

        public String getShortDescription() {return shortDescription;}
        public void setShortDescription(String shortDescription) {this.shortDescription = shortDescription;}

        public String getDescription() {return description;}
        public void setDescription(String description) {this.description = description;}

        public String getVideoURL() {return videoURL;}
        public void setVideoURL(String videoURL) {this.videoURL = videoURL;}

        public String getThumbnailURL() {return thumbnailURL;}
        public void setThumbnailURL(String thumbnailURL) {this.thumbnailURL = thumbnailURL;}

        public StepApi() { }
        public StepApi(int id, String shortDescription, String description, String videoURL, String thumbnailURL) {
            super();
            this.id = id;
            this.shortDescription = shortDescription;
            this.description = description;
            this.videoURL = videoURL;
            this.thumbnailURL = thumbnailURL;
        }
}
