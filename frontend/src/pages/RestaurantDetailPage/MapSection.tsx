import { Wrapper } from '@googlemaps/react-wrapper';
import styled from 'styled-components';
import MapContent from '~/components/@common/Map/MapContent';
import useMediaQuery from '~/hooks/useMediaQuery';
import { BORDER_RADIUS } from '~/styles/common';

interface MapSectionProps {
  lat: number;
  lng: number;
}

function MapSection({ lat, lng }: MapSectionProps) {
  const { isMobile } = useMediaQuery();

  return (
    <StyledMapSection>
      <h5>위치 확인하기</h5>
      <div>
        <Wrapper apiKey={process.env.GOOGLE_MAP_API_KEY} language="ko" libraries={['places']}>
          <MapContent
            center={{ lat, lng }}
            zoom={17}
            style={{ width: '100%', height: isMobile ? '300px' : '600px' }}
            markers={[{ lat, lng }]}
            gestureHandling="cooperative"
          />
        </Wrapper>
      </div>
    </StyledMapSection>
  );
}

export default MapSection;

const StyledMapSection = styled.section`
  display: flex;
  flex-direction: column;
  gap: 2rem 0;

  & > div {
    border-radius: ${BORDER_RADIUS.md};
    overflow: hidden;
  }
`;
