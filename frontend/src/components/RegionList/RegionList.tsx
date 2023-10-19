import { useNavigate } from 'react-router-dom';
import Slider from 'react-slick';
import styled from 'styled-components';
import { Region } from '~/@types/region.types';
import { RegionCarouselSettings } from '~/constants/carouselSettings';
import { RECOMMENDED_REGION } from '~/constants/recommendedRegion';
import { SERVER_IMG_URL } from '~/constants/url';
import useMediaQuery from '~/hooks/useMediaQuery';
import { FONT_SIZE } from '~/styles/common';

function RegionList() {
  const { isMobile } = useMediaQuery();
  const navigate = useNavigate();
  const REGION_LIST = Object.keys(RECOMMENDED_REGION);
  const clickIcon = (region: Region) => {
    navigate(`/region/${region}`);
  };

  return (
    <div>
      {isMobile ? (
        <StyledIconBox>
          {REGION_LIST.map((region: Region) => (
            <StyledRegion
              onClick={() => clickIcon(region)}
              imgUrl={`${SERVER_IMG_URL}regions/${region}.jpeg`}
              aria-label={RECOMMENDED_REGION[region].name.join('')}
            >
              <StyledRegionName>
                {RECOMMENDED_REGION[region].name.map(item => (
                  <>
                    {item}
                    <br />
                  </>
                ))}
              </StyledRegionName>
              <StyledMask />
            </StyledRegion>
          ))}
        </StyledIconBox>
      ) : (
        <StyledSliderContainer>
          <Slider {...RegionCarouselSettings}>
            {REGION_LIST.map((region: Region) => (
              <StyledRegion
                onClick={() => clickIcon(region)}
                imgUrl={`${SERVER_IMG_URL}regions/${region}.jpeg`}
                aria-label={RECOMMENDED_REGION[region].name.join('')}
              >
                <StyledRegionName>
                  {RECOMMENDED_REGION[region].name.map(item => (
                    <>
                      {item}
                      <br />
                    </>
                  ))}
                </StyledRegionName>
                <StyledMask />
              </StyledRegion>
            ))}
          </Slider>
        </StyledSliderContainer>
      )}
    </div>
  );
}

export default RegionList;

const StyledRegion = styled.div<{ imgUrl: string }>`
  display: flex !important;
  justify-content: center;
  align-items: center;

  position: relative;

  width: 64px;
  min-width: 64px;
  max-width: 64px;
  height: 64px;
  min-height: 64px;

  margin: 0 auto;

  border-radius: 100%;
  background-image: ${({ imgUrl }) => `url(${imgUrl})`};
  background-size: cover;

  cursor: pointer;

  overflow: hidden;
`;

const StyledMask = styled.div`
  position: absolute;

  width: 100%;
  height: 100%;

  background-color: black;

  opacity: 0.35;
`;

const StyledRegionName = styled.span`
  z-index: 1;

  color: var(--white);
  font-size: ${FONT_SIZE.sm};
  line-height: 12px;
  text-align: center;
`;

const StyledSliderContainer = styled.div`
  padding: 2rem 4rem;
`;

const StyledIconBox = styled.div`
  display: flex;
  gap: 2rem;

  padding: 1.6rem;

  justify-items: flex-start;

  overflow-x: scroll;

  &::-webkit-scrollbar {
    display: none;
  }
`;
