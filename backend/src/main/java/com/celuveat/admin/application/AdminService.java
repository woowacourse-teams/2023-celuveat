package com.celuveat.admin.application;

import static com.celuveat.admin.exception.AdminExceptionType.ILLEGAL_DATE_FORMAT;
import static com.celuveat.restaurant.domain.SocialMedia.YOUTUBE;

import com.celuveat.admin.exception.AdminException;
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
            RestaurantImage restaurantImage = request.toRestaurantImage(YOUTUBE, restaurant);
            Video video = request.toVideo(celeb, restaurant, toLocalDate(request.videoUploadDate()));

            saveAllData(celeb, restaurant, restaurantImage, video);
        }
    }

    private LocalDate toLocalDate(String rawData) {
        try {
            return LocalDate.parse(rawData, DateTimeFormatter.ofPattern("yyyy. M. d."));
        } catch (DateTimeParseException e) {
            throw new AdminException(ILLEGAL_DATE_FORMAT);
        }
    }

    private Restaurant getOrCreateRestaurant(SaveDataRequest request) {
        return restaurantRepository.findByNameAndRoadAddress(
                request.restaurantName(),
                request.roadAddress()
        ).orElseGet(request::toRestaurant);
    }

    private void saveAllData(Celeb celeb, Restaurant restaurant, RestaurantImage restaurantImage, Video video) {
        celebRepository.save(celeb);
        restaurantRepository.save(restaurant);
        restaurantImageRepository.save(restaurantImage);
        videoRepository.save(video);
    }
}
