import { useEffect, useRef, useState } from 'react';
import styled from 'styled-components';
import { RestaurantImage } from '~/@types/image.type';
import LeftBracket from '~/assets/icons/left-bracket.svg';
import RightBracket from '~/assets/icons/right-bracket.svg';
import { BORDER_RADIUS } from '~/styles/common';
import WaterMarkImage from '../WaterMarkImage';
import useMediaQuery from '~/hooks/useMediaQuery';

interface ImageCarouselProps {
  images: RestaurantImage[];
  type: 'list' | 'map';
}

function ImageCarousel({ images, type }: ImageCarouselProps) {
  const ref = useRef<HTMLDivElement>();
  const [currentIndex, setCurrentIndex] = useState<number>(0);
  const { isMobile } = useMediaQuery();

  const handleScroll = () => {
    const index = Math.round(ref.current.scrollLeft / ref.current.clientWidth);
    setCurrentIndex(index);
  };

  const goToPrevious: React.MouseEventHandler<HTMLButtonElement> = e => {
    e.stopPropagation();
    setCurrentIndex(currentIndex - 1);
  };

  const goToNext: React.MouseEventHandler<HTMLButtonElement> = e => {
    e.stopPropagation();
    setCurrentIndex(currentIndex + 1);
  };

  useEffect(() => {
    ref.current.scrollTo({
      left: currentIndex * ref.current.clientWidth,
      behavior: 'smooth',
    });
  }, [currentIndex]);

  useEffect(() => {
    ref.current.addEventListener('scrollend', handleScroll);
  }, []);

  return (
    <StyledCarouselContainer type={type}>
      <StyledCarouselSlide ref={ref} isMobile={isMobile}>
        {images.map(({ id, name, author }) => (
          <WaterMarkImage key={id} imageUrl={name} waterMark={author} type={type} isMobile={isMobile} />
        ))}
      </StyledCarouselSlide>
      {currentIndex !== 0 && !isMobile && (
        <StyledLeftButton type="button" onClick={goToPrevious}>
          <LeftBracket width={10} height={10} />
        </StyledLeftButton>
      )}
      {currentIndex !== images.length - 1 && !isMobile && (
        <StyledRightButton type="button" onClick={goToNext}>
          <RightBracket width={10} height={10} />
        </StyledRightButton>
      )}
      {images.length > 1 && (
        <StyledDots>
          {images.map((_, index) => (
            <StyledDot isActive={index === currentIndex} />
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

  ::-webkit-scrollbar {
    display: none;
  }
`;

const StyledLeftButton = styled.button`
  left: 12px;
`;

const StyledRightButton = styled.button`
  right: 12px;
`;

const StyledCarouselSlide = styled.div<{ isMobile: boolean }>`
  display: flex;

  width: 100%;
  overflow-x: auto;
  scroll-snap-type: x mandatory;
`;

const StyledDots = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 0 0.5rem;

  position: absolute;
  bottom: 12px;

  width: 100%;
`;

const StyledDot = styled.div<{ isActive: boolean }>`
  width: 8px;
  height: 8px;

  margin: 0 0.5rem;

  border-radius: 50%;
  background-color: ${({ isActive }) => (isActive ? 'var(--gray-4)' : 'var(--white)')};

  cursor: pointer;
`;
