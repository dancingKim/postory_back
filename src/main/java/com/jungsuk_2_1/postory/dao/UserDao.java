package com.jungsuk_2_1.postory.dao;

import com.jungsuk_2_1.postory.dto.ChannelUserDto;
import com.jungsuk_2_1.postory.dto.HeaderDto;
import com.jungsuk_2_1.postory.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    UserDto findByUserEmail(String eId);
    Boolean existsByUserEmail(String eId);
    Boolean existsByUserNic(String nic);
    void save(UserDto userDto);
    void statusSave(String userId);
    String findStatusByUserId(String userId);
    UserDto findByUserId(String userId);
    ChannelUserDto findByChnlUri(String channelUri);
    HeaderDto findHeaderInfoByUserId(String userId);
}