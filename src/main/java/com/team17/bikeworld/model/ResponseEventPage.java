package com.team17.bikeworld.model;

import com.google.gson.annotations.Expose;
import com.team17.bikeworld.entity.Event;

import java.util.List;

public class ResponseEventPage{
    @Expose
    private int totalPage;
    @Expose
    private int currPage;
    @Expose
    private List<Event> content;


        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getCurrPage() {
            return currPage;
        }

        public void setCurrPage(int currPage) {
            this.currPage = currPage;
        }

        public List<Event> getContent() {
            return content;
        }

        public void setContent(List<Event> content) {
            this.content = content;
        }
}
