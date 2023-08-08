import { styled } from 'styled-components';
import { BORDER_RADIUS } from '~/styles/common';
import WaterMarkImage from '../WaterMarkImage';

interface ImageGridProps {
  images: { waterMark: string; url: string }[];
}

function ImageGrid({ images }: ImageGridProps) {
  return (
    <StyledImageGridContainer>
      <StyledMainImage>
        <WaterMarkImage type="list" imageUrl={images[0].url} waterMark={images[0].waterMark} />
      </StyledMainImage>
      <StyledAdditionalImage>
        <WaterMarkImage type="list" imageUrl={images[1].url} waterMark={images[1].waterMark} />
        <WaterMarkImage type="list" imageUrl={images[2].url} waterMark={images[2].waterMark} />
        <WaterMarkImage type="list" imageUrl={images[3].url} waterMark={images[3].waterMark} />
        <WaterMarkImage type="list" imageUrl={images[4].url} waterMark={images[4].waterMark} />
      </StyledAdditionalImage>
    </StyledImageGridContainer>
  );
}

export default ImageGrid;

const StyledImageGridContainer = styled.div`
  display: grid;
  gap: 0.8rem;

  width: 100%;

  border-radius: ${BORDER_RADIUS.md};
  grid-template-columns: 1fr 1fr;
  overflow: hidden;
`;

const StyledMainImage = styled.div`
  width: 100%;
`;

const StyledAdditionalImage = styled.div`
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-template-rows: 1fr 1fr;

  gap: 0.8rem;
`;
