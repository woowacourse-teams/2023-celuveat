package com.celuveat.restaurant.command.application;

import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.auth.command.domain.OauthMemberRepository;
import com.celuveat.restaurant.command.application.dto.SuggestImagesCommand;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantImageSuggestion;
import com.celuveat.restaurant.command.domain.RestaurantImageSuggestionRepository;
import com.celuveat.restaurant.command.domain.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantImageSuggestionService {

    private final RestaurantRepository restaurantRepository;
    private final OauthMemberRepository oauthMemberRepository;
    private final RestaurantImageSuggestionRepository restaurantImageSuggestionRepository;

    public void suggestImages(SuggestImagesCommand command) {
        Restaurant restaurant = restaurantRepository.getById(command.restaurantId());
        OauthMember member = oauthMemberRepository.getById(command.memberId());
        restaurantImageSuggestionRepository.saveAll(command.imageNames().stream()
                .map(imageName -> new RestaurantImageSuggestion(imageName, member, restaurant))
                .toList()
        );
    }
}
