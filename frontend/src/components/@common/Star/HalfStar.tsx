import { styled } from 'styled-components';

import EmptyLeftStarIcon from '~/assets/icons/star/empty-left-star-icon.svg';
import EmptyRightStarIcon from '~/assets/icons/star/empty-right-star-icon.svg';

import type { StarRate } from '~/@types/api.types';

interface HalfStarProps {
  isLeft: boolean;
  isFilled: boolean;
  rateNumber: StarRate;
  size: `${number}px`;
  onRateClick: React.MouseEventHandler<HTMLButtonElement>;
}

function HalfStar({ isLeft, isFilled, rateNumber, size, onRateClick }: HalfStarProps) {
  const halfStarColor = isFilled ? '#FFD601' : '#e8e8e8';

  if (isLeft) {
    return (
      <StyleHalfStarButton type="button" data-rate={rateNumber} size={size} onClick={onRateClick}>
        <EmptyLeftStarIcon fill={halfStarColor} />
      </StyleHalfStarButton>
    );
  }

  return (
    <StyleHalfStarButton type="button" onClick={onRateClick} size={size} data-rate={rateNumber}>
      <EmptyRightStarIcon fill={halfStarColor} />
    </StyleHalfStarButton>
  );
}

export default HalfStar;

const StyleHalfStarButton = styled.button<{ size: `${number}px` }>`
  padding: 0;
  margin: 0;

  border: none;
  background: none;
  outline: none;

  & > svg {
    width: ${({ size }) => size};
  }
`;
