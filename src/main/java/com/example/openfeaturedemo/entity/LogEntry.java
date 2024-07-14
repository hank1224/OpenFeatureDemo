package com.example.openfeaturedemo.entity;

import com.example.openfeaturedemo.dto.LogDataDTO;

import java.util.Date;
import java.util.List;

public class LogEntry {
    private Date timestamp;
    private List<LogDataDTO> data;
    private String logLevel;

    public LogEntry() {}

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public List<LogDataDTO> getData() {
        return data;
    }

    public void setData(List<LogDataDTO> data) {
        this.data = data;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    @Override
    public String toString() {
        return "LogEntry{" +
                "timestamp=" + timestamp +
                ", data=" + data +
                ", logLevel='" + logLevel + '\'' +
                '}';
    }
}
