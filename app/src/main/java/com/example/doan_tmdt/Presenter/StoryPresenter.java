package com.example.doan_tmdt.Presenter;

import com.example.doan_tmdt.Models.Story;
import com.example.doan_tmdt.Models.User;
import com.example.doan_tmdt.my_interface.IStory;
import com.example.doan_tmdt.my_interface.StoryView;
import com.example.doan_tmdt.my_interface.UserView;

public class StoryPresenter implements IStory {

    private Story story;
    private StoryView callback;

    public StoryPresenter(StoryView callback){
        story = new Story(this);
        this.callback = callback;
    }

    public void HandleGetStory(String iduser){
        story.HandleGetStory(iduser);
    }
    @Override
    public void getDataStory(String noidung) {
        callback.getDataStory(noidung);
    }
}
