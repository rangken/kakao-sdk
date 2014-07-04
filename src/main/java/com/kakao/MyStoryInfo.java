/**
 * Copyright 2014 Kakao Corp.
 *
 * Redistribution and modification in source or binary forms are not permitted without specific prior written permission. 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kakao;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kakao.KakaoStoryService.StoryType;

/**
 * 내스토리 요청에 대한 결과 객체로 스토리 정보가 담겨 있다.
 * @author MJ
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MyStoryInfo {
    /**
     * 내스토리 고유 id
     */
    @JsonProperty("id")
    private String id;

    /**
     * 내스토리의 텍스트 내용
     */
    @JsonProperty("content")
    private String content;

    /**
     * 내스토리의 타입
     * NOTE 또는 PHOTO 또는 NOT_SUPPORTED
     */
    @JsonProperty("media_type")
    private String mediaType;

    /**
     * 포스팅한 시각
     * yyyy-mm-ddThh:mm:ss
     */
    @JsonProperty("created_at")
    private String createdAt;

    /**
     * {@link com.kakao.KakaoStoryService.StoryType#PHOTO} 스토리에 한해 이미지 파일 URL list
     */
    @JsonProperty("media")
    private MyStoryImageInfo[] medias;

    /**
     * @return 내스토리 고유 id
     */
    public String getId() {
        return id;
    }

    /**
     * @return 내스토리의 텍스트 내용
     */
    public String getContent() {
        return content;
    }

    /**
     * @return 내스토리의 타입
     */
    public StoryType getMediaType() {
        return StoryType.getType(mediaType);
    }

    /**
     * @return 포스팅한 시각
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * @return 이미지 스토리에 한해 이미지 파일 URL list
     */
    public MyStoryImageInfo[] getMedias() {
        return medias;
    }

    /**
     * 내스토리의 이미지를 크기별로 얻을 수 있는 객체.
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MyStoryImageInfo {
        /**
         * 포스팅한 원본 이미지
         */
        @JsonProperty("original")
        public String original;

        /**
         * 1280 * 1706 리사이징한 이미지
         */
        @JsonProperty("xlarge")
        public String xlarge;

        /**
         * 720 * 960 리사이징한 이미지
         */
        @JsonProperty("large")
        public String large;

        /**
         * 240 * 320 리사이징한 이미지
         */
        @JsonProperty("medium")
        public String medium;

        /**
         * 160 * 213 리사이징한 이미지
         */
        @JsonProperty("small")
        public String small;

        /**
         * @return 포스팅한 원본 이미지
         */
        public String getOriginal() {
            return original;
        }

        /**
         * @return 1280 * 1706 리사이징한 이미지
         */
        public String getXlarge() {
            return xlarge;
        }

        /**
         * @return 720 * 960 리사이징한 이미지
         */
        public String getLarge() {
            return large;
        }

        /**
         * @return 240 * 320 리사이징한 이미지
         */
        public String getMedium() {
            return medium;
        }

        /**
         * @return 160 * 213 리사이징한 이미지
         */
        public String getSmall() {
            return small;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("KakaoStoryActivityImage{");
            sb.append("original='").append(original).append('\'');
            sb.append(", xlarge='").append(xlarge).append('\'');
            sb.append(", large='").append(large).append('\'');
            sb.append(", medium='").append(medium).append('\'');
            sb.append(", small='").append(small).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("KakaoStoryActivity{");
        sb.append("id='").append(id).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append(", mediaType='").append(mediaType).append('\'');
        sb.append(", createdAt='").append(createdAt).append('\'');
        sb.append(", medias=").append(Arrays.toString(medias));
        sb.append('}');
        return sb.toString();
    }
}

