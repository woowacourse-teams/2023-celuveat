import { useState } from 'react';
import styled, { css } from 'styled-components';
import { RestaurantImage } from '~/@types/image.type';
import LeftBracket from '~/assets/icons/left-bracket.svg';
import RightBracket from '~/assets/icons/right-bracket.svg';
import { BORDER_RADIUS } from '~/styles/common';

interface ImageCarouselProps {
  images: RestaurantImage[];
}

function ImageCarousel({ images }: ImageCarouselProps) {
  const [currentIndex, setCurrentIndex] = useState<number>(0);

  const goToPrevious = () => {
    setCurrentIndex(prevIndex => prevIndex - 1);
  };

  const goToNext = () => {
    setCurrentIndex(prevIndex => prevIndex + 1);
  };

  return (
    <StyledCarouselContainer>
      <StyledCarouselSlide currentIndex={currentIndex}>
        {images.map(({ id, name }, index) => (
          <Img key={id} src={name} alt={`${index + 1}`} />
        ))}
      </StyledCarouselSlide>
      {currentIndex !== 0 && (
        <StyledLeftButton type="button" onClick={goToPrevious}>
          <LeftBracket width={10} height={10} />
        </StyledLeftButton>
      )}
      {currentIndex !== images.length - 1 && (
        <StyledRightButton type="button" onClick={goToNext}>
          <RightBracket width={10} height={10} />
        </StyledRightButton>
      )}
      {images.length > 1 && (
        <StyledDots currentIndex={currentIndex}>
          {Array.from({ length: images.length }, () => (
            <StyledDot />
          ))}
        </StyledDots>
      )}
    </StyledCarouselContainer>
  );
}

export default ImageCarousel;

const StyledCarouselContainer = styled.div`
  position: relative;

  width: 315px;
  overflow: hidden;

  border-radius: ${BORDER_RADIUS.md};

  button {
    visibility: hidden;

    display: flex;
    justify-content: center;
    align-items: center;

    position: absolute;
    top: 50%;

    width: 32px;
    height: 32px;

    border: none;
    border-radius: 50%;
    background-color: var(--white);

    cursor: pointer;
    opacity: 0;
    transition: transform 0.15s ease-in-out, opacity 0.2s ease-in-out;
    transform: translateY(-50%);
    box-shadow: var(--shadow);
    outline: none;

    &:hover {
      transform: translateY(-50%) scale(1.04);
    }
  }

  &:hover {
    button {
      visibility: visible;

      opacity: 0.85;

      &:hover {
        opacity: 1;
      }
    }
  }
`;

const StyledLeftButton = styled.button`
  left: 12px;
`;

const StyledRightButton = styled.button`
  right: 12px;
`;

const StyledCarouselSlide = styled.div<{ currentIndex: number }>`
  display: flex;

  width: 100%;
  height: auto;

  transition: transform 0.3s ease-in-out;
  transform: ${({ currentIndex }) => `translateX(-${currentIndex * 100}%)`};
`;

const Img = styled.img`
  width: 100%;
  height: auto;
`;

const StyledDots = styled.div<{ currentIndex: number }>`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 0 0.5rem;

  position: absolute;
  bottom: 12px;

  width: 100%;

  ${({ currentIndex }) => css`
    & > span:nth-child(${currentIndex + 1}) {
      opacity: 1;
      transition: transform 0.2s ease-in-out, opacity 0.2s ease-in-out;
      transform: scale(1.1);
    }
  `}
`;

const StyledDot = styled.span`
  width: 6px;
  height: 6px;

  border-radius: 50%;
  background-color: var(--white);

  opacity: 0.2;
  transition: transform 0.2s ease-in-out, opacity 0.2s ease-in-out;
`;
