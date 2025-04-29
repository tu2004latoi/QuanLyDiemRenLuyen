/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.dtt.repositories;

import com.dtt.pojo.NewsFeed;
import java.util.List;

/**
 *
 * @author admin
 */
public interface NewsFeedRepository {
//    NewsFeed getNewsFeedById(int id);
//    NewsFeed addNewsFeed(NewsFeed n);
    List<NewsFeed> getAllNewsFeeds();
//    boolean deleteNewsFeed(int id);
}
