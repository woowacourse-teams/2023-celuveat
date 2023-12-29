import React from 'react';
import { useNavigate } from 'react-router-dom';
import Slider from 'react-slick';
import styled from 'styled-components';
import ProfileImage from '~/components/@common/ProfileImage';
import { CelebCarouselSettings } from '~/constants/carouselSettings';
import { celebOptions } from '~/constants/celeb';
import useMediaQuery from '~/hooks/useMediaQuery';
import { FONT_SIZE } from '~/styles/common';

function CelebBestSection() {
  const { isMobile } = useMediaQuery();
  const navigate = useNavigate();

  const clickCelebIcon = (id: number) => {
    navigate(`/celeb/${id}`);
  };

  return (
    <section>
      <StyledTitle>셀럽 BEST</StyledTitle>
      {isMobile ? (
        <StyledIconBox>
          {celebOptions.map(celeb => {
            const { name, profileImageUrl, id } = celeb;
            return (
              <StyledCeleb onClick={() => clickCelebIcon(id)}>
                <ProfileImage name={name} imageUrl={profileImageUrl} size="64px" boxShadow />
                <span>{name}</span>
              </StyledCeleb>
            );
          })}
        </StyledIconBox>
      ) : (
        <StyledSliderContainer>
          <Slider {...CelebCarouselSettings}>
            {celebOptions.map(celeb => {
              const { name, profileImageUrl, id } = celeb;
              return (
                <StyledCeleb onClick={() => clickCelebIcon(id)}>
                  <ProfileImage name={name} imageUrl={profileImageUrl} size="64px" boxShadow />
                  <span>{name}</span>
                </StyledCeleb>
              );
            })}
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

const StyledCeleb = styled.div`
  display: flex !important;
  flex-direction: column;
  align-items: center;
  gap: 0.8rem;

  font-size: ${FONT_SIZE.sm};

  cursor: pointer;
`;

const StyledTitle = styled.h5`
  margin-left: 1.6rem;
`;

const StyledSliderContainer = styled.div`
  padding: 2rem 4rem;
`;
