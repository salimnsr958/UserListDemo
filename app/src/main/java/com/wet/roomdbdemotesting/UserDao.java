package com.wet.roomdbdemotesting;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void registerUser(UserEntity userEntity);

    @Query("SELECT * from user_details where userId=(:userID) and password =(:password)")
    UserEntity loginUser(String userID, String password);


    @Query("SELECT * from user_details")
    List<UserEntity> loadAllList();



}
