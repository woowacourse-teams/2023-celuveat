import styled from 'styled-components';
import { BORDER_RADIUS, FONT_SIZE, paintSkeleton } from '~/styles/common';

interface WaterMarkImageProps {
  waterMark: string;
  imageUrl: string;
}

function WaterMarkImage({ waterMark, imageUrl }: WaterMarkImageProps) {
  return (
    <StyledWaterMarkImage>
      <StyledImage src={`${process.env.BASE_URL}/images-data/${imageUrl}`} alt="음식점" loading="lazy" />
      <StyledWaterMark aria-hidden="true">{waterMark}</StyledWaterMark>
    </StyledWaterMarkImage>
  );
}

export default WaterMarkImage;

const StyledWaterMarkImage = styled.div`
  position: relative;

  width: 100%;
  aspect-ratio: 1.05 / 1;

  height: auto;
`;

const StyledImage = styled.img`
  ${paintSkeleton}
  display: block;

  aspect-ratio: 1.05 / 1;
  object-fit: cover;

  width: 100%;
`;

const StyledWaterMark = styled.div`
  position: absolute;
  top: 12px;
  left: 12px;

  padding: 0.4rem 0.8rem;

  border-radius: ${BORDER_RADIUS.xs};
  background-color: var(--white);

  color: var(--black);
  font-size: ${FONT_SIZE.sm};
`;
