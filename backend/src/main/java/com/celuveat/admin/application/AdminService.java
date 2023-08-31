package com.celuveat.admin.application;

import static com.celuveat.admin.exception.AdminExceptionType.ILLEGAL_DATE_FORMAT;
import static com.celuveat.admin.exception.AdminExceptionType.MISMATCH_COUNT_YOUTUBE_VIDEO_LINK_AND_UPLOAD_DATE;

import com.celuveat.admin.exception.AdminException;
import com.celuveat.admin.presentation.dto.SaveCelebRequest;
import com.celuveat.admin.presentation.dto.SaveDataRequest;
import com.celuveat.celeb.domain.Celeb;
import com.celuveat.celeb.domain.CelebRepository;
import com.celuveat.restaurant.domain.Restaurant;
import com.celuveat.restaurant.domain.RestaurantImage;
import com.celuveat.restaurant.domain.RestaurantImageRepository;
import com.celuveat.restaurant.domain.RestaurantRepository;
import com.celuveat.video.domain.Video;
import com.celuveat.video.domain.VideoRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

    private final CelebRepository celebRepository;
    private final RestaurantRepository restaurantRepository;
    private final RestaurantImageRepository restaurantImageRepository;
    private final VideoRepository videoRepository;

    public void saveData(List<SaveDataRequest> requests) {
        for (SaveDataRequest request : requests) {
            Celeb celeb = celebRepository.getByYoutubeChannelName(request.youtubeChannelName());
            Restaurant restaurant = getOrCreateRestaurant(request);
            List<RestaurantImage> restaurantImages = toRestaurantImages(request, restaurant);
            List<Video> videos = toVideos(request, celeb, restaurant);
            saveAllData(restaurant, restaurantImages, videos);
        }
    }

    private Restaurant getOrCreateRestaurant(SaveDataRequest request) {
        return restaurantRepository.findByNameAndRoadAddress(
                request.restaurantName(),
                request.roadAddress()
        ).orElseGet(request::toRestaurant);
    }

    private List<RestaurantImage> toRestaurantImages(SaveDataRequest request, Restaurant restaurant) {
        List<RestaurantImage> images = new ArrayList<>();
        String[] imageNames = request.imageName().split(",");
        for (String imageName : imageNames) {
            RestaurantImage restaurantImage = request.toRestaurantImage(imageName.strip(), restaurant);
            images.add(restaurantImage);
        }
        return images;
    }

    private List<Video> toVideos(SaveDataRequest request, Celeb celeb, Restaurant restaurant) {
        String[] videoUrls = request.youtubeVideoUrl().split(",");
        String[] uploadDates = request.videoUploadDate().split(",");
        if (videoUrls.length != uploadDates.length) {
            throw new AdminException(MISMATCH_COUNT_YOUTUBE_VIDEO_LINK_AND_UPLOAD_DATE);
        }

        List<Video> videos = new ArrayList<>();
        for (int i = 0; i < videoUrls.length; i++) {
            String videoUrl = videoUrls[i].strip();
            String rawUploadDate = uploadDates[i].strip();
            Video video = request.toVideo(videoUrl, toLocalDate(rawUploadDate), celeb, restaurant);
            videos.add(video);
        }
        return videos;
    }

    private LocalDate toLocalDate(String rawData) {
        try {
            return LocalDate.parse(rawData, DateTimeFormatter.ofPattern("yyyy. M. d."));
        } catch (DateTimeParseException e) {
            throw new AdminException(ILLEGAL_DATE_FORMAT);
        }
    }

    private void saveAllData(Restaurant restaurant, List<RestaurantImage> images, List<Video> videos) {
        restaurantRepository.save(restaurant);
        restaurantImageRepository.saveAll(images);
        videoRepository.saveAll(videos);
    }

    public void saveCelebs(List<SaveCelebRequest> requests) {
        List<Celeb> celebs = requests.stream()
                .map(SaveCelebRequest::toCeleb)
                .toList();
        celebRepository.saveAll(celebs);
    }
}
