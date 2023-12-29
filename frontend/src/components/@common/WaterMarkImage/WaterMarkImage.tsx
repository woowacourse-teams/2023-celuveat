import { useState } from 'react';
import { styled, css } from 'styled-components';
import { BORDER_RADIUS, FONT_SIZE, paintSkeleton } from '~/styles/common';
import { getImgUrl } from '~/utils/image';

interface WaterMarkImageProps {
  waterMark?: string;
  imageUrl?: string;
  type: 'list' | 'map';
  sns: string;
}

function WaterMarkImage({ waterMark, imageUrl, type, sns }: WaterMarkImageProps) {
  const [isImageLoading, setIsImageLoading] = useState<boolean>(true);

  const onClickWaterMark = (e: React.MouseEvent) => {
    e.stopPropagation();

    if (sns === 'INSTAGRAM') window.open(`https://www.instagram.com/${waterMark.substring(1)}`, '_blank');
    if (sns === 'YOUTUBE') window.open(`https://www.youtube.com/${waterMark}`, '_blank');
  };

  return (
    <StyledWaterMarkImage type={type} isImageLoading={isImageLoading}>
      <picture>
        <source type="images/webp" srcSet={getImgUrl(imageUrl, 'webp')} />
        <source type="images/jpeg" srcSet={getImgUrl(imageUrl, 'jpeg')} />
        <StyledImage
          src={getImgUrl(imageUrl, 'webp')}
          alt="음식점"
          loading="lazy"
          onLoad={() => setIsImageLoading(false)}
        />
      </picture>
      {waterMark && (
        <StyledWaterMark onClick={onClickWaterMark} aria-hidden="true">
          {waterMark}
        </StyledWaterMark>
      )}
    </StyledWaterMarkImage>
  );
}

export default WaterMarkImage;

const styledImgCssVariable = css`
  position: absolute;
  inset: 0;

  object-fit: cover;

  width: 100%;
  height: 100%;
`;

const StyledWaterMarkImage = styled.div<{ type: 'list' | 'map'; isImageLoading: boolean }>`
  ${({ isImageLoading }) =>
    isImageLoading &&
    css`
      ${paintSkeleton}
      background: none;
    `}
  position: relative;
  overflow: hidden;

  width: 100%;
  min-width: 100%;

  border-radius: ${BORDER_RADIUS.md};

  ${({ type }) => (type === 'list' ? `padding-top: 95%;` : `padding-top: 65%;`)}

  scroll-snap-align: start;
  scroll-snap-stop: always;
`;

const StyledImage = styled.img`
  ${styledImgCssVariable}
`;

const StyledWaterMark = styled.div.attrs({})`
  position: absolute;
  top: 1%;
  left: 1%;

  padding: 0.4rem 0.8rem;

  border-radius: ${BORDER_RADIUS.xs};

  color: var(--white);
  font-size: ${FONT_SIZE.sm};
  font-weight: 700;

  cursor: pointer;
`;
