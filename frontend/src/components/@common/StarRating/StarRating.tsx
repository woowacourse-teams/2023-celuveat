import { ComponentPropsWithoutRef } from 'react';
import { styled } from 'styled-components';
import Star from '~/components/@common/Star/Star';

export type StarRate = 0 | 0.5 | 1 | 1.5 | 2 | 2.5 | 3 | 3.5 | 4 | 4.5 | 5;

const starRateList: StarRate[] = [0.5, 1, 1.5, 2, 2.5, 3, 3.5, 4, 4.5, 5];

interface StarRatingProps extends ComponentPropsWithoutRef<'div'> {
  rate: StarRate;
}

function StarRating({ rate, ...restProps }: StarRatingProps) {
  return (
    <StyledStarRatingWrapper {...restProps}>
      {starRateList.map((starRate, idx) => {
        const isLeft = idx % 2 === 0;
        const isFilled = starRate <= rate;

        return <Star isLeft={isLeft} isFilled={isFilled} rateNumber={starRate} />;
      })}
    </StyledStarRatingWrapper>
  );
}

export default StarRating;

const StyledStarRatingWrapper = styled.div`
  display: flex;

  width: 100%;

  div:nth-child(2n-1) {
    margin-left: 0.6rem;
  }
`;
