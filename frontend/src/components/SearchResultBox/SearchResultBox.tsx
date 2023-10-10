import styled from 'styled-components';
import MiniRestaurantCard from '../MiniRestaurantCard';
import { RestaurantData, RestaurantListData } from '~/@types/api.types';

interface SearchResultBoxProps {
  restaurantDataList: RestaurantListData;
}

function SearchResultBox({ restaurantDataList }: SearchResultBoxProps) {
  return (
    <StyledResultBox>
      {restaurantDataList &&
        restaurantDataList.content?.map(({ celebs, ...restaurant }: RestaurantData) => (
          <MiniRestaurantCard
            key={`${restaurant.id}${celebs[0].id}`}
            restaurant={restaurant}
            celebs={celebs}
            showWaterMark={false}
            showLike
            showRating
          />
        ))}
    </StyledResultBox>
  );
}

export default SearchResultBox;

const StyledResultBox = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2.4rem;
`;
