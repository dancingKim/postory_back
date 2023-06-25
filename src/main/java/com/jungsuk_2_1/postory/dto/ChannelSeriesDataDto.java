package com.jungsuk_2_1.postory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChannelSeriesDataDto {
    private ChannelDto channel;
    private ChannelUserDto channelUser;
    private List<ChannelSeriesDto> channelSerieses;
}
