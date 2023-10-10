import { ComponentPropsWithoutRef } from 'react';
import { styled } from 'styled-components';
import HalfStar from '~/components/@common/Star/HalfStar';

import type { StarRate } from '~/@types/api.types';

const starRateList: StarRate[] = [0.5, 1, 1.5, 2, 2.5, 3, 3.5, 4, 4.5, 5];

interface StarRatingProps extends ComponentPropsWithoutRef<'div'> {
  rate: StarRate;
  size?: `${number}px`;
  onRateClick?: React.MouseEventHandler<HTMLButtonElement>;
}

function StarRating({ rate, onRateClick, size = '20px', ...restProps }: StarRatingProps) {
  return (
    <StyledStarRatingWrapper {...restProps}>
      {starRateList.map((starRate, idx) => {
        const isLeft = idx % 2 === 0;
        const isFilled = starRate <= rate;

        return (
          <HalfStar size={size} isLeft={isLeft} isFilled={isFilled} rateNumber={starRate} onRateClick={onRateClick} />
        );
      })}
    </StyledStarRatingWrapper>
  );
}

export default StarRating;

const StyledStarRatingWrapper = styled.div`
  display: flex;

  width: max-content;

  button:nth-child(2n) {
    margin-right: 1.2rem;
  }
`;
