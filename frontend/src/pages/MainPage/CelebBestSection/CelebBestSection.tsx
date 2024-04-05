import React from 'react';
import Slider from 'react-slick';
import styled from 'styled-components';
import CelebProfile from '~/components/CelebProfile';
import { CelebCarouselSettings } from '~/constants/carouselSettings';
import { celebOptions } from '~/constants/celeb';
import useMediaQuery from '~/hooks/useMediaQuery';

function CelebBestSection() {
  const { isMobile } = useMediaQuery();

  return (
    <section>
      <StyledTitle>셀럽 BEST</StyledTitle>
      {isMobile ? (
        <StyledIconBox>
          {celebOptions.map(celeb => (
            <CelebProfile {...celeb} />
          ))}
        </StyledIconBox>
      ) : (
        <StyledSliderContainer>
          <Slider {...CelebCarouselSettings}>
            {celebOptions.map(celeb => (
              <CelebProfile {...celeb} />
            ))}
          </Slider>
        </StyledSliderContainer>
      )}
    </section>
  );
}

export default CelebBestSection;

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

const StyledTitle = styled.h5`
  margin-left: 1.6rem;
`;

const StyledSliderContainer = styled.div`
  padding: 2rem 4rem;
`;
