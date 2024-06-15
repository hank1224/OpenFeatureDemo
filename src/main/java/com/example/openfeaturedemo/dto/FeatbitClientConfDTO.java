package com.example.openfeaturedemo.dto;


public class FeatbitClientConfDTO {

    private String clientKey;

    private String featbitStreamingURL;

    private String featbitEventURL;

    public FeatbitClientConfDTO(String clientKey, String featbitStreamingURL, String featbitEventURL) {
        this.clientKey = clientKey;
        this.featbitStreamingURL = featbitStreamingURL;
        this.featbitEventURL = featbitEventURL;
    }

    public String getClientKey() {
        return clientKey;
    }

    public void setClientKey(String clientKey) {
        this.clientKey = clientKey;
    }

    public String getFeatbitStreamingURL() {
        return featbitStreamingURL;
    }

    public void setFeatbitStreamingURL(String featbitStreamingURL) {
        this.featbitStreamingURL = featbitStreamingURL;
    }

    public String getFeatbitEventURL() {
        return featbitEventURL;
    }

    public void setFeatbitEventURL(String featbitEventURL) {
        this.featbitEventURL = featbitEventURL;
    }
}
