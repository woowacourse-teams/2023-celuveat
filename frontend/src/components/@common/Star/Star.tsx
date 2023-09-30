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
  onRateClick: React.MouseEventHandler<HTMLButtonElement>;
}

function HalfStar({ isLeft, isFilled, rateNumber, onRateClick }: HalfStarProps) {
  if (isLeft) {
    return (
      <StyleHalfStarButton type="button" data-rate={rateNumber} onClick={onRateClick}>
        {isFilled ? <FilledLeftStarIcon /> : <EmptyLeftStarIcon />}
      </StyleHalfStarButton>
    );
  }

  return (
    <StyleHalfStarButton type="button" onClick={onRateClick} data-rate={rateNumber}>
      {isFilled ? <FilledRightStarIcon /> : <EmptyRightStarIcon />}
    </StyleHalfStarButton>
  );
}

export default HalfStar;

const StyleHalfStarButton = styled.button`
  padding: 0;
  margin: 0;

  border: none;
  background: none;
  outline: none;
`;
