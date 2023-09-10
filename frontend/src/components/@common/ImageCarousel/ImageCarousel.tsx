import { useEffect, useRef } from 'react';
import styled, { css } from 'styled-components';
import { RestaurantImage } from '~/@types/image.type';
import LeftBracket from '~/assets/icons/left-bracket.svg';
import RightBracket from '~/assets/icons/right-bracket.svg';
import { BORDER_RADIUS } from '~/styles/common';
import WaterMarkImage from '../WaterMarkImage';
import useCarousel from '~/hooks/useCarousel';

interface ImageCarouselProps {
  images: RestaurantImage[];
  type: 'list' | 'map';
}

function ImageCarousel({ images, type }: ImageCarouselProps) {
  const ref = useRef<HTMLDivElement>();
  const { currentIndex, goToPrevious, goToNext, isMoving } = useCarousel(ref, images.length);

  useEffect(() => {
    ref.current.style.transform = `translateX(-${100 * currentIndex}%)`;
    ref.current.style.transition = isMoving ? 'none' : `transform 0.3s ease-in-out`;
  }, [currentIndex, isMoving]);

  return (
    <StyledCarouselContainer type={type}>
      <StyledCarouselSlide currentIndex={currentIndex} ref={ref}>
        {images.map(({ id, name, author }) => (
          <WaterMarkImage key={id} imageUrl={name} waterMark={author} type={type} />
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

const StyledCarouselContainer = styled.div<{ type: 'list' | 'map' }>`
  position: relative;

  width: 100%;
  overflow: hidden;

  border-radius: ${({ type }) =>
      type === 'list' ? `${BORDER_RADIUS.md};` : `${BORDER_RADIUS.md} ${BORDER_RADIUS.md} 0 0;`}
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
