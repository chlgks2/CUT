package com.example.model.dto;

public class responseDTO {

    private long[] Id = new long[30];

    public responseDTO(){}

    public long[] getSongId() {
        return Id;
    }

    public void setSongId(long[] songId) {
        this.Id = songId;
    }

    @Override
    public String toString() {
        return "responseDTO{" +
                "songId=" + Id +
                '}';
    }
}
