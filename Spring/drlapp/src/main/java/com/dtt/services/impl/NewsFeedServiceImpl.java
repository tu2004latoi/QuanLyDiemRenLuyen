/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.services.impl;

import com.dtt.pojo.NewsFeed;
import com.dtt.repositories.NewsFeedRepository;
import com.dtt.services.NewsFeedService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author MR TU
 */
@Service
public class NewsFeedServiceImpl implements NewsFeedService{
    @Autowired
    private NewsFeedRepository newsFeedRepo;
    
    @Override
    public List<NewsFeed> getAllNewsFeeds(){
        return this.newsFeedRepo.getAllNewsFeeds();
    }
}
