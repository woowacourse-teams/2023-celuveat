package com.celuveat.admin.application;

import static com.celuveat.restaurant.domain.SocialMedia.YOUTUBE;

import com.celuveat.admin.presentation.dto.SaveDataRequest;
import com.celuveat.celeb.domain.Celeb;
import com.celuveat.celeb.domain.CelebRepository;
import com.celuveat.restaurant.domain.Restaurant;
import com.celuveat.restaurant.domain.RestaurantImage;
import com.celuveat.restaurant.domain.RestaurantImageRepository;
import com.celuveat.restaurant.domain.RestaurantRepository;
import com.celuveat.restaurant.domain.SocialMedia;
import com.celuveat.video.domain.Video;
import com.celuveat.video.domain.VideoRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
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

    public void save(List<SaveDataRequest> requests) {
        for (SaveDataRequest request : requests) {
            Celeb celeb = celebRepository.getByYoutubeChannelName(request.youtubeChannelName());
            Restaurant restaurant = getOrCreateRestaurant(request);
            RestaurantImage restaurantImage = createRestaurantImage(request, YOUTUBE, restaurant);
            Video video = createVideo(request, celeb, restaurant);

            saveAll(celeb, restaurant, restaurantImage, video);
        }
    }

    private Restaurant getOrCreateRestaurant(SaveDataRequest request) {
        Optional<Restaurant> foundRestaurant = restaurantRepository.findByNameAndRoadAddress(
                request.restaurantName(),
                request.roadAddress()
        );
        return foundRestaurant.orElseGet(() -> Restaurant.builder()
                .name(request.restaurantName())
                .category(request.category())
                .roadAddress(request.roadAddress())
                .latitude(Double.parseDouble(request.latitude()))
                .longitude(Double.parseDouble(request.longitude()))
                .phoneNumber(request.phoneNumber())
                .naverMapUrl(request.naverMapUrl())
                .build());
    }

    private RestaurantImage createRestaurantImage(
            SaveDataRequest request,
            SocialMedia socialMedia,
            Restaurant restaurant
    ) {
        return RestaurantImage.builder()
                .name(request.imageName())
                .author(request.youtubeChannelName())
                .socialMedia(socialMedia)
                .restaurant(restaurant)
                .build();
    }

    private Video createVideo(SaveDataRequest request, Celeb celeb, Restaurant restaurant) {
        return Video.builder()
                .youtubeUrl(request.youtubeVideoUrl())
                .uploadDate(LocalDate.parse(request.videoUploadDate(), DateTimeFormatter.ofPattern("yyyy. M. d.")))
                .celeb(celeb)
                .restaurant(restaurant)
                .build();
    }

    private void saveAll(Celeb celeb, Restaurant restaurant, RestaurantImage restaurantImage, Video video) {
        celebRepository.save(celeb);
        restaurantRepository.save(restaurant);
        restaurantImageRepository.save(restaurantImage);
        videoRepository.save(video);
    }
}
