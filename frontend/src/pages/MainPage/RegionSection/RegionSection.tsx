import React from 'react';
import styled from 'styled-components';
import RegionList from '~/components/RegionList';

function RegionSection() {
  return (
    <section>
      <StyledTitle>어디로 가시나요?</StyledTitle>
      <RegionList />
    </section>
  );
}

export default RegionSection;

const StyledTitle = styled.h5`
  margin-left: 1.6rem;
`;
