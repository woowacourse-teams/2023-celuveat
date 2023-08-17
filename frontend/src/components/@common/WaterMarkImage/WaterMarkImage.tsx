import styled from 'styled-components';
import { BORDER_RADIUS, FONT_SIZE, paintSkeleton } from '~/styles/common';

interface WaterMarkImageProps {
  waterMark: string;
  imageUrl?: string;
  type: 'list' | 'map';
}

function WaterMarkImage({ waterMark, imageUrl, type }: WaterMarkImageProps) {
  return (
    <StyledWaterMarkImage type={type}>
      <StyledImage src={`https://dev.celuveat.com/images-data/${imageUrl}`} alt="음식점" loading="lazy" />
      {waterMark && <StyledWaterMark aria-hidden="true">{waterMark}</StyledWaterMark>}
    </StyledWaterMarkImage>
  );
}

export default WaterMarkImage;

const StyledWaterMarkImage = styled.div<{ type: 'list' | 'map' }>`
  ${paintSkeleton}
  position: relative;

  width: 100%;
  min-width: 100%;
  ${({ type }) => (type === 'list' ? `padding-top: 95%;` : `padding-top: 65%;`)}
`;

const StyledImage = styled.img`
  position: absolute;
  inset: 0;

  object-fit: cover;

  width: 100%;
  height: 100%;
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
