import { styled } from 'styled-components';
import { RestaurantModalInfo } from '~/@types/restaurant.types';
import { BORDER_RADIUS, FONT_SIZE } from '~/styles/common';
import TextButton from '../@common/Button';

interface MapModalContentProps {
  content: RestaurantModalInfo;
}

function MapModalContent({ content }: MapModalContentProps) {
  const { name, roadAddress, phoneNumber, images, naverMapUrl } = content;
  return (
    <StyledMapModalContent>
      <StyledRestaurantInfo>
        <div>
          <h4>{name}</h4>
          <div>{roadAddress}</div>
          <div>{phoneNumber}</div>
        </div>
        <StyledRestaurantImage
          src="https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMzAzMjhfMjY1%2FMDAxNjc5OTk0NjYxMDI5.Mo-i3h1Q8kR4yi0hOL2lQZdA6t6uiQ599aBNnnJ83q8g._NGlnMeHtVCiJVWenUbbtICefoddkW1Wg0g3PCxn9Q4g.JPEG.twinkle_paul%2F100V7467-2.jpg"
          alt={`${name} 식당 이미지`}
        />
        {/* <img src={`./${images[0].name}`} alt={`${name} 식당 이미지`} /> */}
      </StyledRestaurantInfo>
      <TextButton
        type="button"
        text="네이버 지도에서 보기"
        colorType="dark"
        onClick={() => {
          window.open(naverMapUrl, '_blank');
        }}
      />
    </StyledMapModalContent>
  );
}

export default MapModalContent;

const StyledMapModalContent = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2.4rem;
`;

const StyledRestaurantInfo = styled.div`
  display: flex;
  justify-content: space-between;

  & > div:first-child {
    display: flex;
    flex-direction: column;
    gap: 0.8rem;

    font-size: ${FONT_SIZE.md};
  }
`;

const StyledRestaurantImage = styled.img`
  width: 64px;
  height: 64px;

  border-radius: ${BORDER_RADIUS.sm};
`;
