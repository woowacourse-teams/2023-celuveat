import styled from 'styled-components';
import useMediaQuery from '~/hooks/useMediaQuery';
import BannerSlider from './components/BannerSlider';

function BannerSection() {
  const { isMobile } = useMediaQuery();

  return (
    <section>
      {isMobile ? (
        <BannerSlider />
      ) : (
        <StyledSliderContainer>
          <BannerSlider />
        </StyledSliderContainer>
      )}
    </section>
  );
}

export default BannerSection;

const StyledSliderContainer = styled.div`
  padding: 2rem 4rem;
`;
