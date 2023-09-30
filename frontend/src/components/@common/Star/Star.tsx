import { styled } from 'styled-components';
import EmptyLeftStarIcon from '~/assets/icons/star/empty-left-star-icon.svg';
import EmptyRightStarIcon from '~/assets/icons/star/empty-right-star-icon.svg';
import FilledLeftStarIcon from '~/assets/icons/star/filled-left-star-icon.svg';
import FilledRightStarIcon from '~/assets/icons/star/filled-right-star-icon.svg';
import { StarRate } from '~/components/@common/StarRating/StarRating';

interface HalfStarProps {
  isLeft: boolean;
  isFilled: boolean;
  rateNumber: StarRate;
}

function HalfStar({ isLeft, isFilled, rateNumber }: HalfStarProps) {
  if (isLeft) {
    return (
      <StyledIsHalfStyleWrapper data-rate={rateNumber}>
        {isFilled ? <FilledLeftStarIcon /> : <EmptyLeftStarIcon />}
      </StyledIsHalfStyleWrapper>
    );
  }

  return (
    <StyledIsHalfStyleWrapper data-rate={rateNumber}>
      {isFilled ? <FilledRightStarIcon /> : <EmptyRightStarIcon />}
    </StyledIsHalfStyleWrapper>
  );
}

export default HalfStar;

const StyledIsHalfStyleWrapper = styled.div`
  display: flex;
  gap: 0;
`;
