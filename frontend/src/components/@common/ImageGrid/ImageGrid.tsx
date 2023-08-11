import { styled } from 'styled-components';
import { BORDER_RADIUS } from '~/styles/common';
import WaterMarkImage from '../WaterMarkImage';

interface ImageGridProps {
  images: { waterMark: string; url: string }[];
}

function ImageGrid({ images }: ImageGridProps) {
  const makeAdditionalImage = () => {
    const additionalImages = images.slice(1);
    const imagesLength = additionalImages.length;

    if (imagesLength < 4) {
      const noImages = Array.from({ length: 4 - imagesLength }, () => ({ waterMark: '', url: '' }));
      return [...additionalImages, ...noImages];
    }

    return additionalImages.slice(1, 4);
  };

  return (
    <StyledImageGridContainer>
      <StyledMainImage>
        <WaterMarkImage type="list" imageUrl={images[0].url} waterMark={images[0].waterMark} />
      </StyledMainImage>
      <StyledAdditionalImage>
        {makeAdditionalImage().map(({ url, waterMark }) =>
          url ? (
            <WaterMarkImage type="list" imageUrl={url} waterMark={waterMark} />
          ) : (
            <StyledNoImage>🥺사진이 더이상 없어요🥺</StyledNoImage>
          ),
        )}
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

const StyledNoImage = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  width: 100%;
  height: 100%;

  background-color: var(--gray-1);

  font-size: ${BORDER_RADIUS.lg};
`;
